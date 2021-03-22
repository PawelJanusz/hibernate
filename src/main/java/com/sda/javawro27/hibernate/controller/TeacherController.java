package com.sda.javawro27.hibernate.controller;

import com.sda.javawro27.hibernate.dao.EntityDao;
import com.sda.javawro27.hibernate.model.Student;
import com.sda.javawro27.hibernate.model.Teacher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.Scanner;

public class TeacherController {

    //create
    public void addTeacher(Scanner scanner) {
        EntityDao<Teacher> dao = new EntityDao<>();

        System.out.println("Podaj parametry: IMIE NAZWISKO ");

        String linia = scanner.nextLine();
        String[] slowa = linia.split(" ");

        Teacher teacher = new Teacher(slowa[0], slowa[1]);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            teacher.setBirthDate(simpleDateFormat.parse(slowa[2]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dao.saveOrUpdate(teacher);
    }

    public void connectTeacher(Scanner scanner){
        EntityDao<Student> daoS = new EntityDao<>();
        EntityDao<Teacher> daoT = new EntityDao<>();

        System.out.println("Podaj parametry: Identyfikator studenta");
        Long idS = Long.valueOf(scanner.nextLine());

        System.out.println("Podaj parametry: Identyfikator nauczyciela");
        Long idT = Long.valueOf(scanner.nextLine());


        Optional<Student> studentOptional = daoS.findById(Student.class, idS);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();                                // z bazy pobierz 1 obiekt relacji

            Optional<Teacher> teacherOptional = daoT.findById(Teacher.class, idT);
            if (teacherOptional.isPresent()) {
                Teacher teacher = teacherOptional.get();                            // z bazy pobierz 2 obiekt relacji

                student.getTeacherSet().add(teacher);
                daoS.saveOrUpdate(student);
            }

        } else {
            // todo: exception
            System.err.println("Brak studenta o podanym id");
        }
    }
}
