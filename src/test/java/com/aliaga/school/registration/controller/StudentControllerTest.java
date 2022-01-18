package com.aliaga.school.registration.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StudentControllerTest {

    @Autowired
    MockMvc mockMvc;

    private Path workingDir;

    @BeforeEach
    void init() {
        this.workingDir = Path.of("", "src/test/resources");
    }

    @Test
    void testCreateStudent_Success() throws Exception {
        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readPayloadAsString("sample-student.json"))
        ).andExpect(status().isOk());
    }

    @Test
    void testReadStudent_Success() throws Exception {
        mockMvc.perform(get("/students")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void testReadStudentById_Success() throws Exception {
        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readPayloadAsString("sample-student.json"))
        ).andExpect(status().isOk());

        mockMvc.perform(get("/students/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void testReadStudentById_Failure() throws Exception {
        mockMvc.perform(get("/students/2")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testUpdateStudent_Success() throws Exception {
        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readPayloadAsString("sample-student.json"))
        ).andExpect(status().isOk());

        mockMvc.perform(put("/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readPayloadAsString("sample-updated-student.json"))
        ).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("jane"));
    }

    @Test
    void testDeleteStudent_Success() throws Exception {
        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readPayloadAsString("sample-student.json"))
        ).andReturn();

        mockMvc.perform(delete("/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readPayloadAsString("sample-updated-student.json"))
        ).andExpect(status().isOk());

        mockMvc.perform(get("/students/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    private String readPayloadAsString(String filename) throws IOException {
        Path file = this.workingDir.resolve(filename);
        return Files.readString(file);
    }
}