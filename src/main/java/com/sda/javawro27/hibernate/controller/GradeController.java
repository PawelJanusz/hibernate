package com.sda.javawro27.hibernate.controller;

import com.sda.javawro27.hibernate.dao.EntityDao;
import com.sda.javawro27.hibernate.model.Grade;
import com.sda.javawro27.hibernate.model.GradeSubject;
import com.sda.javawro27.hibernate.model.Student;

import java.util.Optional;
import java.util.Scanner;

public class GradeController {

    public void addGradeToStudent(Scanner scanner) {
        EntityDao<Grade> gradeDao = new EntityDao<>();
        EntityDao<Student> studentDao = new EntityDao<>();

        System.out.println("Podaj parametry: Identyfikator");
        Long id = Long.valueOf(scanner.nextLine());

        Optional<Student> studentOptional = studentDao.findById(Student.class, id);
        if(studentOptional.isPresent()) {
            System.out.println("Podaj parametry: GradeValue, Subject[ J_POLSKI,\n" +
                    "    J_ANGIELSKI,\n" +
                    "    MATEMATYKA,\n" +
                    "    INFORMATYKA]");
            String linia = scanner.nextLine();
            double gValue = Double.parseDouble(linia.split(" ")[0]);
            GradeSubject subject = GradeSubject.valueOf(linia.split(" ")[1].toUpperCase());

            // tworzymy ocenę
            Grade grade = new Grade(gValue, subject);
            // 1. stworzenie obiektu w bazie (niepowiązanego)
            gradeDao.saveOrUpdate(grade);


            // 2. powiązanie obiektów
            Student student = studentOptional.get();
            grade.setStudentRef(student);

            // 3. zapis obiektów
            gradeDao.saveOrUpdate(grade);
        }
    }

    public void listStudentGrades(Scanner scanner) {
        EntityDao<Student> dao = new EntityDao<>();

        // nie da się usunąć rekordu po id (bezpośrednio z sesji)
        System.out.println("Podaj parametry: Identyfikator");
        Long id = Long.valueOf(scanner.nextLine());

        Optional<Student> studentOptional = dao.findById(Student.class, id);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();

            student.getGradeList()
                    .forEach(System.out::println);
        }
    }


    public void delGrade(Scanner scanner) {
        EntityDao<Grade> dao = new EntityDao<>();

        // nie da się usunąć rekordu po id (bezpośrednio z sesji)
        System.out.println("Podaj parametry: Identyfikator");
        Long id = Long.valueOf(scanner.nextLine());

        Optional<Grade> gradeOptional = dao.findById(Grade.class, id);
        if (gradeOptional.isPresent()) {
            Grade grade = gradeOptional.get();
            dao.delete(grade);
        }
    }
}
