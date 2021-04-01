package com.sda.javawro27.hibernate.dao;

import com.sda.javawro27.hibernate.model.Behaviour;
import com.sda.javawro27.hibernate.model.Grade;
import com.sda.javawro27.hibernate.model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentDaoTest {

    private StudentDao studentDao = new StudentDao();
    private EntityDao<Student> entityDao = new EntityDao<>();


    @Test
    void findByAgeBetweenReturnListWithStudents(){
        //given
        Student student = new Student("Malgorzata", "Wrobel", 1.76, 21, true, Behaviour.GOOD);
        //when
        entityDao.saveOrUpdate(student);
        List<Student> list = studentDao.findByAgeBetween(21, 21);
        //then
        assertNotNull(list);
    }

    @Test
    void lastNameShouldBeEqualWithAddRecord(){
        //given
        Set<Grade> studentSet = new HashSet<>();
        Student student = new Student("Alek", "Bolko", 1.9, 23, true, Behaviour.ACCEPTABLE, studentSet);
        List<Student> beforeInsert = Collections.singletonList(student);
        //when
        entityDao.saveOrUpdate(student);
        List<Student> afterSearch = studentDao.findByLastName(Student.class, "Bolko");
        //then
        assertEquals(beforeInsert, afterSearch);
        entityDao.delete(student);
    }

    @Test
    void listShouldBeNotEmptyAfterSearchByBehaviourAndAlive(){
        //given
        Set<Grade> studentSet = new HashSet<>();
        Student student = new Student("Kasia", "Poleska", 1.9, 23, true, Behaviour.ACCEPTABLE, studentSet);
        //when
        entityDao.saveOrUpdate(student);
        List<Student> afterSearch = studentDao.findByBehaviourAndAlive(Behaviour.ACCEPTABLE, true);
        //then
        assertNotNull(afterSearch);
        entityDao.delete(student);
    }
}