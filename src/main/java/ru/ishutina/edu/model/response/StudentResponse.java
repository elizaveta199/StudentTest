package ru.ishutina.edu.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private Integer id;
    private String name;
    private List<Integer> marks;
}
