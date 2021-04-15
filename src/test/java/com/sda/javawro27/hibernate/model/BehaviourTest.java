package com.sda.javawro27.hibernate.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class BehaviourTest {


    @ParameterizedTest
    @EnumSource(Behaviour.class)
    void gradeSubjectShouldBeShorterThan20Characters(Behaviour behaviour){
        assertThat(behaviour.toString().length(), greaterThan(2));
        assertThat(behaviour.toString().length(), greaterThanOrEqualTo(3));
    }

    @ParameterizedTest
    @EnumSource(Behaviour.class)
    void gradeSubjectShouldBeEndWithIOrA(Behaviour behaviour){
        String[] behaviourList = {"EXEMPLARY", "GOOD", "ACCEPTABLE", "BAD", "CRIMINAL"};
        assertAll(
                () -> behaviour.toString().endsWith("d"),
                () -> behaviour.toString().endsWith("e"),
                () -> behaviour.toString().equals(behaviourList)
        );
    }

}
