package ru.ishutina.edu.student;

import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ishutina.edu.model.request.StudentRequest;

import java.util.List;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteStudentTest {

    private StudentRequest studentFirst;
    private StudentApi studentApi;
    private int id;

    @BeforeEach
    public void setUp() {
        studentApi = new StudentApi();
        id = (int) (Math.random() * 100 + 1);
        studentFirst = new StudentRequest(id, "Egor", List.of(5, 5, 5));
        studentApi.postStudent(studentFirst);
    }

    @AfterEach
    public void down() {
        studentApi.deleteStudent(id);
    }

    @Test
    @DisplayName("Удаление студента")
    public void deleteStudentTest() {
        Response response = studentApi.deleteStudent(id);
        assertEquals(SC_OK, response.statusCode());
    }

    @Test
    @DisplayName("Ошибка 400 при получении несуществующего студента")
    public void deleteStudentNotFoundTest() {
        studentApi.deleteStudent(id);
        Response response = studentApi.deleteStudent(id);
        assertEquals(SC_NOT_FOUND, response.statusCode());
    }
}