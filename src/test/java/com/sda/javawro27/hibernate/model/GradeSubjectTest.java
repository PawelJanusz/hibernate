package com.sda.javawro27.hibernate.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class GradeSubjectTest {

    @ParameterizedTest
    @EnumSource(GradeSubject.class)
    void gradeSubjectShouldBeShorterThan20Characters(GradeSubject gradeSubject){
        assertThat(gradeSubject.toString().length(), lessThan(20));
    }

    @ParameterizedTest
    @EnumSource(GradeSubject.class)
    void gradeSubjectShouldBeEndWithIOrA(GradeSubject gradeSubject){
        String[] gradeList = {"J_POLSKI", "J_ANGIELSKI", "MATEMATYKA", "INFORMATYKA"};
        assertAll(
                () -> gradeSubject.toString().endsWith("i"),
                () -> gradeSubject.toString().endsWith("a"),
                () -> gradeSubject.toString().equals(gradeList)
        );
    }
}
