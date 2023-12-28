package com.vt.valuetogether.global.jwt;

import static com.vt.valuetogether.global.meta.ResultCode.NOT_FOUND_USER;
import static com.vt.valuetogether.global.meta.ResultCode.SYSTEM_ERROR;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vt.valuetogether.domain.user.dto.request.UserLocalLoginReq;
import com.vt.valuetogether.domain.user.dto.response.UserLocalLoginRes;
import com.vt.valuetogether.global.exception.GlobalException;
import com.vt.valuetogether.global.redis.RedisUtil;
import com.vt.valuetogether.global.response.RestResponse;
import com.vt.valuetogether.global.security.UserDetailsImpl;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final int REFRESH_TOKEN_TIME = 60 * 24 * 14;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void setup() {
        setFilterProcessesUrl("/api/v1/users/login");
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            UserLocalLoginReq req =
                    new ObjectMapper().readValue(request.getInputStream(), UserLocalLoginReq.class);

            return getAuthenticationManager()
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword(), null));

        } catch (IOException e) {
            throw new GlobalException(SYSTEM_ERROR);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult)
            throws IOException {

        UserLocalLoginRes res = addTokensInHeader(authResult, response);
        settingResponse(response, RestResponse.success(res));
    }

    private UserLocalLoginRes addTokensInHeader(
            Authentication authResult, HttpServletResponse response) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String username = userDetails.getUsername();

        List<? extends GrantedAuthority> list = new ArrayList<>(authResult.getAuthorities());
        String role = list.get(0).getAuthority();

        String accessToken = jwtUtil.createAccessToken(username, role);
        String refreshToken = jwtUtil.createRefreshToken();

        response.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, accessToken);
        response.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, refreshToken);

        redisUtil.set(jwtUtil.substringToken(refreshToken), username, REFRESH_TOKEN_TIME);

        return new UserLocalLoginRes();
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        settingResponse(response, RestResponse.error(NOT_FOUND_USER));
    }

    private void settingResponse(HttpServletResponse response, RestResponse<?> res)
            throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String result = objectMapper.writeValueAsString(res);
        response.getWriter().write(result);
    }
}
