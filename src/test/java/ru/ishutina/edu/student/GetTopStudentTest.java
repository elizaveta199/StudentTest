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

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetTopStudentTest {

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
        studentFirst = new StudentRequest(id, "Pop", List.of(5, 5, 5, 4));
        studentSecond = new StudentRequest(id + 1, "Alex", List.of(5, 4, 5, 4));
        studentThird = new StudentRequest(id + 2, "Vanya", List.of(5, 5, 4, 4));
        studentFourth = new StudentRequest(id + 3, "Elena", List.of(5, 4, 5, 4, 5, 4));
    }

    @AfterEach
    public void down() {
        studentApi.deleteStudent(id);
        studentApi.deleteStudent(id + 1);
        studentApi.deleteStudent(id + 2);
        studentApi.deleteStudent(id + 3);
    }


    @Test
    @DisplayName("Получение пустого рейтинга, когда в бд нет студентов")
    public void getTopStudentNullStudentTest() {
        Response response = studentApi.getTopStudent();
        assertEquals(SC_OK, response.statusCode());
        assertTrue(response.asString().isEmpty());
    }

    @Test
    @DisplayName("Получение рейтинга среди 2-x студентов с разными оценками и средним балом")
    public void getTopStudentTest1() {
        studentApi.postStudent(studentFirst);
        studentApi.postStudent(studentSecond);
        Response response = studentApi.getTopStudent();

        StudentResponse[] studentResponse = response.as(StudentResponse[].class);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(SC_OK).isEqualTo(response.statusCode());
        softly.assertThat(studentFirst.getId()).as("id Не совпадает").isEqualTo(studentResponse[0].getId());
        softly.assertThat(studentFirst.getName()).as("name не совпадает").isEqualTo(studentResponse[0].getName());
        softly.assertThat(studentFirst.getMarks()).as("marks не совпадает").isEqualTo(studentResponse[0].getMarks());
        softly.assertAll();
    }


    @Test
    @DisplayName("Получение рейтинга среди 2-x студентов с одинаковыми оценками и средним балом")
    public void getTopStudentRest2() {
        studentApi.postStudent(studentSecond);
        studentApi.postStudent(studentThird);
        Response response = studentApi.getTopStudent();

        StudentResponse[] studentResponse = response.as(StudentResponse[].class);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(SC_OK).isEqualTo(response.statusCode());
        softly.assertThat(studentSecond.getId()).as("id Не совпадает").isEqualTo(studentResponse[0].getId());
        softly.assertThat(studentSecond.getName()).as("name не совпадает").isEqualTo(studentResponse[0].getName());
        softly.assertThat(studentSecond.getMarks()).as("marks не совпадает").isEqualTo(studentResponse[0].getMarks());
        softly.assertThat(studentThird.getId()).as("id Не совпадает").isEqualTo(studentResponse[1].getId());
        softly.assertThat(studentThird.getName()).as("name не совпадает").isEqualTo(studentResponse[1].getName());
        softly.assertThat(studentThird.getMarks()).as("marks не совпадает").isEqualTo(studentResponse[1].getMarks());
        softly.assertAll();
    }

    @Test
    @DisplayName("Получение рейтинга среди 2-x студентов с одинаковым средним балом, но разным количеством оценок")
    public void getTopStudentRest3() {
        studentApi.postStudent(studentSecond);
        studentApi.postStudent(studentFourth);
        Response response = studentApi.getTopStudent();

        StudentResponse[] studentResponse = response.as(StudentResponse[].class);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(SC_OK).isEqualTo(response.statusCode());
        softly.assertThat(studentFourth.getId()).as("id Не совпадает").isEqualTo(studentResponse[0].getId());
        softly.assertThat(studentFourth.getName()).as("name не совпадает").isEqualTo(studentResponse[0].getName());
        softly.assertThat(studentFourth.getMarks()).as("marks не совпадает").isEqualTo(studentResponse[0].getMarks());
        softly.assertAll();
    }
}