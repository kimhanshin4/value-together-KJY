package com.vt.valuetogether.domain.oauth;

import com.vt.valuetogether.domain.oauth.dto.request.OAuth2LoginReq;
import com.vt.valuetogether.domain.oauth.exception.OAuth2ProviderInvalidException;
import com.vt.valuetogether.domain.user.entity.Provider;
import com.vt.valuetogether.global.meta.ResultCode;
import java.util.Arrays;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OAuth2Attributes {
    GITHUB("GITHUB") {
        @Override
        public OAuth2LoginReq of(Map<String, Object> attributes, String username) {
            return OAuth2LoginReq.builder()
                    .username(username)
                    .oauthId(attributes.get("id").toString())
                    .email((String) attributes.get("email"))
                    .imageUrl((String) attributes.get("avatar_url"))
                    .provider(Provider.GITHUB)
                    .build();
        }
    },
    NAVER("NAVER") {
        @Override
        @SuppressWarnings("unchecked")
        public OAuth2LoginReq of(Map<String, Object> attributes, String username) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            return OAuth2LoginReq.builder()
                    .username(username)
                    .oauthId((String) response.get("id"))
                    .email((String) response.get("email"))
                    .imageUrl((String) response.get("profile_image"))
                    .provider(Provider.NAVER)
                    .build();
        }
    },
    GOOGLE("GOOGLE") {
        @Override
        public OAuth2LoginReq of(Map<String, Object> attributes, String username) {
            return OAuth2LoginReq.builder()
                    .username(username)
                    .oauthId((String) attributes.get("sub"))
                    .email((String) attributes.get("email"))
                    .imageUrl((String) attributes.get("picture"))
                    .provider(Provider.GOOGLE)
                    .build();
        }
    };

    private final String providerName;

    public static OAuth2LoginReq extract(
            String providerName, Map<String, Object> attributes, String username) {
        return Arrays.stream(values())
                .filter(provider -> providerName.equals(provider.providerName))
                .findAny()
                .orElseThrow(() -> new OAuth2ProviderInvalidException(ResultCode.INVALID_OAUTH_PROVIDER))
                .of(attributes, username);
    }

    public abstract OAuth2LoginReq of(Map<String, Object> attributes, String username);
}
