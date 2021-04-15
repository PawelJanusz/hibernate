package com.sda.javawro27.hibernate.dao;

import com.sda.javawro27.hibernate.model.Behaviour;
import com.sda.javawro27.hibernate.model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TeacherDaoTest {

    private TeacherDao teacherDao;

    @BeforeEach
    void initializeTeacherDao(){
        teacherDao = new TeacherDao();
    }

    @AfterEach
    void cleanAfterEach(){
        teacherDao.clean();
    }

    @Test
    void studentsSetShouldBeEmpty(){
        //given
        //when
        Set<Student> list = teacherDao.findStudents(34L);
        //then
        assertThat(list, empty());
        assertThat(list.size(), not(equalTo(34)));
    }

    @Test
    void studentsSet() {
        //given
        EntityDao<Student> entityDao = new EntityDao<>();
        Student student = new Student("Marek", "Kowalski", 1.87, 34, true, Behaviour.EXEMPLARY);
        //when
        entityDao.saveOrUpdate(student);
        Set<Student> list = teacherDao.findStudents(10L);
        //then
        assertThat(list, not(hasItem(student)));
    }

    @Test
    void exceptionShouldCatch() {
        //given
        EntityDao<Student> entityDao = new EntityDao<>();
        Student student = new Student("Marek", "Kowalski", 1.87, 34, true, Behaviour.EXEMPLARY);
        //when
        
        //then
        assertDoesNotThrow(() -> entityDao.saveOrUpdate(student));

    }

}
