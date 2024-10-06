package org.codegym.springsecurity.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity

@Table(name = "groupes")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Ten group khong duoc trong")
    @Size(min = 2)
    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private List<Student> students;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Student> getStudents() {
        return students;
    }
}
