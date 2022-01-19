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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RegistrationControllerTest {

    @Autowired
    MockMvc mockMvc;

    private Path workingDir;

    @BeforeEach
    void init() {
        this.workingDir = Path.of("", "src/test/resources");
    }

    @Test
    void testCreateRegistration_Success() throws Exception {
        createStudentAndCourse();
        mockMvc.perform(post("/registration-courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readPayloadAsString("sample-registration.json"))
        ).andExpect(status().isOk());
    }

    @Test
    void testCreateRegistration_TwiceInSameCourse() throws Exception {
        createRegistration();
        mockMvc.perform(post("/registration-courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readPayloadAsString("sample-registration.json"))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testCreateRegistration_CourseAtMaxCapacity() throws Exception {
        createRegistration();
        createSecondCourse();
        mockMvc.perform(post("/registration-courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readPayloadAsString("sample-another-registration.json"))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testGetStudentsByCourse_Success() throws Exception {
        createRegistration();
        mockMvc.perform(get("/registration-courses/students?course-id=1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string("[{\"id\":1,\"firstname\":\"john\",\"lastname\":\"doe\"}]"));
    }

    @Test
    void testGetCoursesByStudent_Success() throws Exception {
        createRegistration();
        mockMvc.perform(get("/registration-courses/courses?student-id=1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string("[{\"id\":1,\"course-name\":\"How to live forever\"}]"));
    }

    @Test
    void testGetCoursesWithoutStudents() throws Exception {
        createRegistration();
        createSecondCourse();
        mockMvc.perform(get("/registration-courses/courses/without-students")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string("[{\"id\":2,\"course-name\":\"Improve your coding skills\"}]"));
    }

    @Test
    void testGetStudentsWithoutCourses() throws Exception {
        createRegistration();
        createSecondStudent();
        mockMvc.perform(get("/registration-courses/students/without-courses")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string("[{\"id\":2,\"firstname\":\"jane\",\"lastname\":\"doe\"}]"));
    }

    private void createStudentAndCourse() throws Exception {
        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readPayloadAsString("sample-student.json"))
        ).andExpect(status().isOk());

        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readPayloadAsString("sample-course.json"))
        ).andExpect(status().isOk());
    }

    private void createSecondStudent() throws Exception {
        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readPayloadAsString("sample-another-student.json"))
        ).andExpect(status().isOk());
    }

    private void createSecondCourse() throws Exception {
        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readPayloadAsString("sample-another-course.json"))
        ).andExpect(status().isOk());
    }

    private void createRegistration() throws Exception {
        createStudentAndCourse();
        mockMvc.perform(post("/registration-courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readPayloadAsString("sample-registration.json"))
        ).andExpect(status().isOk());
    }

    private String readPayloadAsString(String filename) throws IOException {
        Path file = this.workingDir.resolve(filename);
        return Files.readString(file);
    }
}