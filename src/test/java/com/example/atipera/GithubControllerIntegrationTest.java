package com.example.atipera;

import com.example.atipera.response.ErrorResponse;
import com.example.atipera.response.RepositoryResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@AutoConfigureMockMvc
class GithubControllerIntegrationTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @Test
    public void getResponseWhenUserNotExistsShouldReturnNotFound() throws Exception {
        // given
        ResultActions performUserNotExists = mockMvc.perform(get("/api/github/nonexistentuserasdfasdf/repos")
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // when
        String contentAsJson = performUserNotExists.andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ErrorResponse errorResponse = objectMapper.readValue(contentAsJson, ErrorResponse.class);
        //then
        assertAll(
                () -> assertThat(errorResponse.getStatus()).isEqualTo(404),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("User not found")
        );
    }

    @Test
    public void getResponseWhenUserExistsShouldReturnRepositoryList() throws Exception {
        // given
        ResultActions performUserRepository = mockMvc.perform(get("/api/github/SzymonBartkowiak43/repos")
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // when

        String contentAsJson = performUserRepository.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<RepositoryResponse> responseList = objectMapper.readValue(contentAsJson, new TypeReference<>() {
        });

        //then
        assertAll(
                () -> assertThat(responseList.size()).isGreaterThan(20)
        );
    }

//    @Test
//    public void getResponseShouldNotContainsForkRepository() throws Exception {
//        // given
//        ResultActions performUserRepository = mockMvc.perform(get("/api/github/SzymonBartkowiak43/repos")
//                .contentType(MediaType.APPLICATION_JSON_VALUE));
//
//
//        // when
//        String contentAsJson = performUserRepository.andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        List<RepositoryResponse> responseList = objectMapper.readValue(contentAsJson, new TypeReference<>() {
//        });
//
//        //then
//        assertAll(
//                () -> assertThat(responseList).allMatch(repo -> !repo.getBranches().)
//        );
//    }




}