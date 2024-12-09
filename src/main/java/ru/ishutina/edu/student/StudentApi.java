package ru.ishutina.edu.student;

import io.restassured.response.Response;
import ru.ishutina.edu.model.request.StudentRequest;

public class StudentApi extends BaseApiClient {
    public Response postStudent(StudentRequest student) {
        return getPostSpec()
                .body(student)
                .post("/student");
    }

    public Response getStudent(int id) {
        return getPostSpec()
                .get("/student/" + id);
    }

    public Response deleteStudent(int id) {
        return getPostSpec()
                .delete("/student/" + id);
    }

    public Response getTopStudent() {
        return getPostSpec()
                .get("/topStudent/");
    }
}
