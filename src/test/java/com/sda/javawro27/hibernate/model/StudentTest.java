package com.sda.javawro27.hibernate.model;

import com.sda.javawro27.hibernate.dao.EntityDao;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void studentsShouldBeNotEqual(){
        //given
        Student student1 = new Student("Artur", "Barecki", 1.7, 22, true, Behaviour.BAD);
        Student student2 = new Student("Artur", "Barecki", 1.7, 21, true, Behaviour.BAD);
        //when
        //then
        assertNotEquals(student1, student2);
    }

    @Test
    void lastNameShouldNotBeNullThenThrowsIllegalStateException(){
        //given
        Student student = new Student("Karolina", null, 1.6, 21, true, Behaviour.ACCEPTABLE);
        EntityDao<Student> dao = new EntityDao<>();
        //then
        assertThrows(IllegalStateException.class, () -> dao.saveOrUpdate(student));
    }

    @Test
    void behaviourCanBeNull(){
        //given
        Student student = new Student("Agata", "Dobra", 1.76, 23, true, null);
        EntityDao<Student> dao = new EntityDao<>();
        int listSizeBefore = dao.findAll(Student.class).size();
        //when
        dao.saveOrUpdate(student);
        int listSizeAfter = dao.findAll(Student.class).size();
        //then
        assertThat(listSizeAfter, equalTo(listSizeBefore + 1));
    }



}