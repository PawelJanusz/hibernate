package com.sda.javawro27.hibernate.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity // jest to klasa bazodanowa
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
@AllArgsConstructor

public class Teacher implements LastNameSearchable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;

    @ManyToMany(mappedBy = "teacherSet")
    private Set<Student> studentSet;

    public Teacher(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Teacher(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Teacher(String firstName, String lastName, Date birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
}
