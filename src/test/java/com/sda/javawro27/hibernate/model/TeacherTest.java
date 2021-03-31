package com.sda.javawro27.hibernate.model;

import com.sda.javawro27.hibernate.dao.EntityDao;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {

    @Test
    void teachersShouldBeEqual(){
        //given
        Teacher teacher1 = new Teacher("Iwona", "Sybis");
        Teacher teacher2 = new Teacher("Iwona", "Sybis");
        //when
        //then
        assertEquals(teacher1, teacher2);
    }

    @Test
    void birthDateCanBeNull(){
        //given
        Teacher teacher = new Teacher("Stanisław", "Hak", null);
        EntityDao<Teacher> dao = new EntityDao<>();
        int listSizeBefore = dao.findAll(Teacher.class).size();
        //when
        dao.saveOrUpdate(teacher);
        int listSizeAfter = dao.findAll(Teacher.class).size();
        //then
        assertThat(listSizeAfter, equalTo(listSizeBefore + 1));
    }

    @Test
    void lastNameShouldNotBeNullThenThrowsIllegalStateException(){
        //given
        Teacher teacher = new Teacher("Karolina", null);
        EntityDao<Teacher> dao = new EntityDao<>();
        //then
        assertThrows(IllegalStateException.class, () -> dao.saveOrUpdate(teacher));
    }

}