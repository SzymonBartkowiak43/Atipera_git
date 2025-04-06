package com.example.atipera.controller;

import com.example.atipera.response.ExceptionResponse;
import com.example.atipera.response.RepositoryResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.RestClient;

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

    @Autowired
    private RestClient.Builder restClientBuilder;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.bindTo(restClientBuilder).build();
    }


    @Test
    public void getResponseWhenUserNotExistsShouldReturnNotFound() throws Exception {
        // given
        String username = "nonexistentuserasdfasdf";
        ResultActions performUserNotExists = mockMvc.perform(get("/api/github/" + username + "/repos")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // when
        String contentAsJson = performUserNotExists.andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ExceptionResponse errorResponse = objectMapper.readValue(contentAsJson, ExceptionResponse.class);
        //then
        assertAll(
                () -> assertThat(errorResponse.getStatus()).isEqualTo(404),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("User not found")
        );
    }

    @Test
    public void getResponseWhenUserExistsShouldReturnRepositoryList() throws Exception {
        // given
        String username = "SzymonBartkowiak43";
        ResultActions performUserRepository = mockMvc.perform(get("/api/github/" + username + "/repos")
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

    @Test
    public void getResponseShouldNotContainsForkRepository() throws Exception {
        // given
        String username = "SzymonBartkowiak43";
        ResultActions performUserRepository = mockMvc.perform(get("/api/github/" + username + "/repos")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // when
        String contentAsJson = performUserRepository.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<RepositoryResponse> responseList = objectMapper.readValue(contentAsJson, new TypeReference<>() {
        });

        List<String> knownForkNames = List.of("forkForTest ", "ForkRepository");

        List<String> returnedRepoNames = responseList.stream()
                .map(RepositoryResponse::getRepositoryName)
                .toList();

        assertThat(returnedRepoNames).doesNotContainAnyElementsOf(knownForkNames);
    }

}