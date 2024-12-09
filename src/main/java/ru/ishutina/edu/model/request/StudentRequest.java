package ru.ishutina.edu.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Data
@AllArgsConstructor
public class StudentRequest {
    private Integer id;
    private String name;
    private List<Integer> marks;
}
