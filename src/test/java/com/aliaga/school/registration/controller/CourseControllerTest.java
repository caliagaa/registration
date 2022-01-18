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
class CourseControllerTest {

    @Autowired
    MockMvc mockMvc;

    private Path workingDir;

    @BeforeEach
    void init() {
        this.workingDir = Path.of("", "src/test/resources");
    }

    @Test
    void testCreateCourse_Success() throws Exception {
        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readPayloadAsString("sample-course.json"))
        ).andExpect(status().isOk());
    }

    @Test
    void testReadCourse_Success() throws Exception {
        mockMvc.perform(get("/courses")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void testReadCourseById_Success() throws Exception {
        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readPayloadAsString("sample-course.json"))
        ).andExpect(status().isOk());

        mockMvc.perform(get("/courses/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void testReadCourseById_Failure() throws Exception {
        mockMvc.perform(get("/courses/2")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testUpdateCourse_Success() throws Exception {
        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readPayloadAsString("sample-course.json"))
        ).andExpect(status().isOk());

        mockMvc.perform(put("/courses/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readPayloadAsString("sample-updated-course.json"))
        ).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.course-name").value("How to earn more money"));
    }

    @Test
    void testDeleteCourse_Success() throws Exception {
        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readPayloadAsString("sample-course.json"))
        ).andReturn();

        mockMvc.perform(delete("/courses/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readPayloadAsString("sample-updated-course.json"))
        ).andExpect(status().isOk());

        mockMvc.perform(get("/courses/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    private String readPayloadAsString(String filename) throws IOException {
        Path file = this.workingDir.resolve(filename);
        return Files.readString(file);
    }
}