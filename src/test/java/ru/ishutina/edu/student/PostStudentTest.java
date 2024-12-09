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

import static org.apache.http.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostStudentTest {

    private StudentRequest studentFirst;
    private StudentRequest studentSecond;
    private StudentRequest studentThird;
    private StudentRequest studentFourth;
    private StudentApi studentApi;
    private int id;

    @BeforeEach
    public void setUp() {
        studentApi = new StudentApi();
        id = (int) (Math.random() * 100 + 1);
        studentFirst = new StudentRequest(id, "Lena", List.of(4, 4, 3, 5));
        studentSecond = new StudentRequest(null, "Tom", List.of(2, 2, 2));
        studentThird = new StudentRequest(id, "Leo", List.of(1, 1));
        studentFourth = new StudentRequest(id, null, List.of(4, 4, 4));
    }

    @AfterEach
    public void down() {
        studentApi.deleteStudent(id);
    }

    @Test
    @DisplayName("Добавление студента")
    public void addStudentTest() {
        Response response = studentApi.postStudent(studentFirst);
        assertEquals(SC_CREATED, response.statusCode());
    }


    @Test
    @DisplayName("Обновление студента")
    public void updateStudentTest() {
        studentApi.postStudent(studentFirst);
        Response response = studentApi.postStudent(studentThird);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(SC_CREATED).isEqualTo(response.statusCode());
        softly.assertAll();
    }

    @Test
    @DisplayName("Добавление студента с id null")
    public void addIdNullStudentTest() {
        Response response = studentApi.postStudent(studentSecond);
        System.out.println(response.asString());

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(SC_CREATED).isEqualTo(response.statusCode());
        softly.assertAll();

        int newId = Integer.parseInt(response.asString());

        Response response1 = studentApi.getStudent(newId);
        StudentResponse studentResponse1 = response1.as(StudentResponse.class);
        SoftAssertions softly1 = new SoftAssertions();
        softly1.assertThat(SC_OK).as("status неверный").isEqualTo(response1.statusCode());
        softly1.assertThat(newId).as("id присвоен неверно").isEqualTo(studentResponse1.getId());
        softly1.assertAll();

        studentApi.deleteStudent(newId);
    }


    @Test
    @DisplayName("Ошибка 400 при добавлении студента без имени")
    public void addStudentWithoutName() {
        Response response = studentApi.postStudent(studentFourth);
        assertEquals(SC_BAD_REQUEST, response.statusCode());
    }
}
