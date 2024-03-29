package com.sda.javawro27.hibernate.dao;

import com.sda.javawro27.hibernate.launch.HibernateUtil;
import com.sda.javawro27.hibernate.model.Student;
import com.sda.javawro27.hibernate.model.Teacher;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.HashSet;
import java.util.Set;

public class TeacherDao {

    private final Set<Student> students = new HashSet<>();


    public Set<Student> findStudents(Long identifier){

        SessionFactory sessionFactory = HibernateUtil.getOurSessionFactory();
        try(Session session = sessionFactory.openSession()){

            //istnieje prawdopodobieństwo, że rekord nie zostanie odnaleziony
            Teacher teacher = session.get(Teacher.class, identifier);
//            teacher.getStudentSet().forEach(System.out::println);

            //ważne by wywołać zapytanie "gettera" na set studentów wewnątrz bloku sesji
            students.addAll(teacher.getStudentSet());
        }catch (HibernateException ex){
            ex.printStackTrace();
        }
        return students;
    }

    public void clean(){
        students.clear();
    }

}
