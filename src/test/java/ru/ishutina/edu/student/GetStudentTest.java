package ru.ishutina.edu.student;

import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ishutina.edu.model.request.StudentRequest;
import ru.ishutina.edu.model.response.StudentResponse;

import java.util.List;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetStudentTest {

    private StudentRequest studentFirst;
    private StudentApi studentApi;
    private int id;

    @BeforeEach
    public void setUp() {
        studentApi = new StudentApi();
        id = (int) (Math.random() * 100 + 1);
        studentFirst = new StudentRequest(id, "Lena", List.of(4, 3, 5, 5));
    }

    @AfterEach
    public void down() {
        studentApi.deleteStudent(id);
    }

    @Test
    @DisplayName("Получение студента")
    public void getStudentTest() {
        studentApi.postStudent(studentFirst);
        Response response = studentApi.getStudent(id);
        StudentResponse studentResponse = response.as(StudentResponse.class);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(SC_OK).isEqualTo(response.statusCode());
        softly.assertThat(studentFirst.getId()).as("id неравен").isEqualTo(studentResponse.getId());
        softly.assertThat(studentFirst.getName()).as("name не совпадает").isEqualTo(studentResponse.getName());
        softly.assertThat(studentFirst.getMarks()).as("marks не совпадает").isEqualTo(studentResponse.getMarks());
        softly.assertAll();
    }


    @Test
    @DisplayName("Ошибка 400 при получении несуществующего студента")
    public void getStudentNotFoundTest() {
        Response response = studentApi.getStudent(-1);
        assertEquals(SC_NOT_FOUND, response.statusCode());
    }
}