package com.vt.valuetogether.domain.checklist.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vt.valuetogether.domain.BaseMvcTest;
import com.vt.valuetogether.domain.checklist.dto.request.ChecklistSaveReq;
import com.vt.valuetogether.domain.checklist.dto.response.ChecklistSaveRes;
import com.vt.valuetogether.domain.checklist.service.ChecklistService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = {ChecklistController.class})
class ChecklistControllerTest extends BaseMvcTest {
    @MockBean private ChecklistService checklistService;

    @Test
    @DisplayName("checklist 저장 테스트")
    void checklist_저장() throws Exception {
        Long checklistId = 1L;
        String title = "title";
        ChecklistSaveReq checklistSaveReq = ChecklistSaveReq.builder().title(title).build();
        ChecklistSaveRes checklistSaveRes = ChecklistSaveRes.builder().checklistId(checklistId).build();
        when(checklistService.saveChecklist(any())).thenReturn(checklistSaveRes);
        this.mockMvc
                .perform(
                        post("/api/v1/checklists")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(checklistSaveReq)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
