package com.sda.javawro27.hibernate.dao;

import com.sda.javawro27.hibernate.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class EntityDaoTest {

    @Test
    void studentShouldBeAddedToTheList(){
        //given
        EntityDao<Student> dao = new EntityDao<>();
        Student student = new Student("Artur", "Kowalski", 1.67, 23, true, Behaviour.BAD);
        int listSizeBefore = dao.findAll(Student.class).size();
        //when
        dao.saveOrUpdate(student);
        int listSizeAfter = dao.findAll(Student.class).size();
        //then
        assertThat(listSizeAfter, is(listSizeBefore + 1));
    }

    @Test
    void twoTeachersShouldBeAddedToTheList(){
        //given
        EntityDao<Teacher> dao = new EntityDao<>();
        Teacher teacher1 = new Teacher("Henryk", "Horoski");
        Teacher teacher2 = new Teacher("Henryk", "Horoski");
        int listSizeBefore = dao.findAll(Teacher.class).size();
        //when
        dao.saveOrUpdate(teacher1);
        dao.saveOrUpdate(teacher2);
        int listSizeAfter = dao.findAll(Teacher.class).size();
        //then
        assertThat(listSizeAfter, is(listSizeBefore + 2));

    }

    @Test
    void gradeShouldBeDeleteFromList(){
        //given
        EntityDao<Grade> dao = new EntityDao<>();
        Grade grade = new Grade(3.5, GradeSubject.J_POLSKI);
        int sizeListBefore = dao.findAll(Grade.class).size();
        //when
        dao.delete(grade);
        int sizeListAfter = dao.findAll(Grade.class).size();
        //then
        assertThat(sizeListAfter, is(sizeListBefore));
    }

    @Test
    void listStudentsShouldBeNotEqualAfterAddNewStudent(){
        //given
        EntityDao<Student> dao = new EntityDao<>();
        Student student = new Student("Arek", "Norek", 1.67, 21, true, Behaviour.ACCEPTABLE);
        //when
        List<Student> list1 = dao.findAll(Student.class);
        dao.saveOrUpdate(student);
        List<Student> list2 = dao.findAll(Student.class);
        //then
        assertNotEquals(list1, list2);
    }

}