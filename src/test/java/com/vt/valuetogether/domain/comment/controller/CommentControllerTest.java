package com.vt.valuetogether.domain.comment.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vt.valuetogether.domain.BaseMvcTest;
import com.vt.valuetogether.domain.comment.dto.request.CommentSaveReq;
import com.vt.valuetogether.domain.comment.dto.response.CommentSaveRes;
import com.vt.valuetogether.domain.comment.service.CommentService;
import com.vt.valuetogether.test.CommentTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = {CommentController.class})
class CommentControllerTest extends BaseMvcTest implements CommentTest {

    @MockBean private CommentService commentService;

    @Test
    @DisplayName("댓글 저장 api 테스트")
    void saveCommentTest() throws Exception {
        // given
        CommentSaveReq req =
                CommentSaveReq.builder().cardId(TEST_CARD_ID).content(TEST_COMMENT_CONTENT).build();
        CommentSaveRes res = CommentSaveRes.builder().commentId(TEST_COMMENT_ID).build();
        when(commentService.saveComment(req)).thenReturn(res);

        // when - then
        mockMvc
                .perform(
                        post("/api/v1/comments")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
