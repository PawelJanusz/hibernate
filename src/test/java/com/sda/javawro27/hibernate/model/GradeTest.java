package com.sda.javawro27.hibernate.model;

import com.sda.javawro27.hibernate.dao.EntityDao;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class GradeTest {

    @Test
    void gradesShouldBeEqual(){
        //given
        Grade grade1 = new Grade(3.7, GradeSubject.J_POLSKI);
        Grade grade2 = new Grade(3.7, GradeSubject.J_POLSKI);
        //when
        //then
        assertEquals(grade1, grade2);
    }

    @Test
    void subjectCanBeNull(){
        //given
        Grade grade = new Grade(5.5, null);
        EntityDao<Grade> dao = new EntityDao<>();
        int listSizeBefore = dao.findAll(Grade.class).size();
        //when
        dao.saveOrUpdate(grade);
        int listSizeAfter = dao.findAll(Grade.class).size();
        //then
        assertThat(listSizeAfter, is(listSizeBefore + 1));
    }
}