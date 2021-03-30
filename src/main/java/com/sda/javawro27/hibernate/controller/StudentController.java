package com.sda.javawro27.hibernate.controller;

import com.sda.javawro27.hibernate.dao.EntityDao;
import com.sda.javawro27.hibernate.dao.StudentDao;
import com.sda.javawro27.hibernate.dao.TeacherDao;
import com.sda.javawro27.hibernate.model.Behaviour;
import com.sda.javawro27.hibernate.model.Grade;
import com.sda.javawro27.hibernate.model.Student;
import com.sda.javawro27.hibernate.model.Teacher;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class StudentController {

    //create
    public void addStudents(Scanner scanner) {
        EntityDao<Student> dao = new EntityDao<>();

        System.out.println("Podaj parametry: IMIE NAZWISKO WZROST WIEK ŻYWY ZACHOWANIE");
        String linia = scanner.nextLine();
        String[] slowa = linia.split(" ");

        Student student = Student.builder()
                .firstName(slowa[0])
                .lastName(slowa[1])
                .height(Double.parseDouble(slowa[2]))
                .age(Integer.parseInt(slowa[3]))
                .alive(Boolean.parseBoolean(slowa[4]))
                .behaviour(Behaviour.valueOf(slowa[5].toUpperCase()))
                .build();
        dao.saveOrUpdate(student);
    }
    //read
    public void listStudents(Scanner scanner){
        System.out.println("Podaj parametry: Identyfikator nauczyciela");
        Long idT = Long.valueOf(scanner.nextLine());

        new TeacherDao().findStudents(idT)
                .forEach(System.out::println);
    }


    public void listStudents(){
        EntityDao<Student> dao = new EntityDao<>();
        System.out.println("Lista studentów");
        dao.findAll(Student.class)
                .forEach(System.out::println);
    }

    //update
    public void updateStudent(Scanner scanner) {
        EntityDao<Student> dao = new EntityDao<>();

        System.out.println("Podaj parametry: Identyfikator");
        Long id = Long.valueOf(scanner.nextLine());

        Optional<Student> studentOptional = dao.findById(Student.class, id);   // szukamy studenta
        if (studentOptional.isPresent()) {                       // jeśli uda się go odnaleźć
            Student student = studentOptional.get();            // wyciągamy studenta z Optional (Box, opakowanie)
            System.out.println("Próbujesz edytować rekord: " + student);

            System.out.println("Podaj parametry: IMIE NAZWISKO WZROST WIEK ŻYWY ZACHOWANIE");
            String linia = scanner.nextLine();
            String[] slowa = linia.split(" ");

            student = Student.builder()
                    .firstName(slowa[0])
                    .lastName(slowa[1])
                    .height(Double.parseDouble(slowa[2]))
                    .age(Integer.parseInt(slowa[3]))
                    .alive(Boolean.parseBoolean(slowa[4]))
                    .id(id)
                    .behaviour(Behaviour.valueOf(slowa[5].toUpperCase()))
                    .build();

            dao.saveOrUpdate(student);
        } else {
            System.err.println("Error, student z takim id nie istnieje.");
        }
    }

    //delete
    public void deleteStudent(Scanner scanner) {
        EntityDao<Student> dao = new EntityDao<>();
        EntityDao<Grade> daoG = new EntityDao<>();

        // nie da się usunąć rekordu po id (bezpośrednio z sesji)
        System.out.println("Podaj parametry: Identyfikator");
        Long id = Long.valueOf(scanner.nextLine());

        Optional<Student> studentOptional = dao.findById(Student.class, id);   // szukamy studenta
        if (studentOptional.isPresent()) {                       // jeśli uda się go odnaleźć
            Student student = studentOptional.get();            // wyciągamy studenta z Optional (Box, opakowanie)

            // usuwamy obiekty w relacji:
            // po pierwsze - usuwamy obiekty w relacji:
            student.getGradeList().forEach(daoG::delete);

            dao.delete(student);                                // przekazujemy do usunięcia
        }
    }


    public void findByLastName(Scanner scanner){
        System.out.println("Podaj parametry: nazwisko");
        String last = scanner.nextLine();

        StudentDao studentDao = new StudentDao();
        List<Student> studentList = studentDao.findByLastName(Student.class, last);
        List<Teacher> teacherList = studentDao.findByLastName(Teacher.class, last);
    }



    public void findByAge(StudentDao dao, Scanner scanner){
        System.out.println("Podaj parametry: AgeFrom, AgeTo");
        String linia = scanner.nextLine();
        int ageFrom = Integer.valueOf(linia.split(" ")[0]);
        int ageTo = Integer.valueOf(linia.split(" ")[1]);

        System.out.println("Znalezione rekordy: ");
        dao.findByAgeBetween(ageFrom, ageTo).forEach(System.out::println);
    }

    public void findByBehaviourAndAlive(StudentDao dao, Scanner scanner){
        System.out.println("Podaj parametry: Behaviour, Alive");
        String linia = scanner.nextLine();

        Behaviour behaviour = Behaviour.valueOf(linia.split(" ")[0]);
        boolean alive = Boolean.parseBoolean(linia.split(" ")[1]);

        System.out.println("Znalezione rekordy: ");
        dao.findByBehaviourAndAlive(behaviour, alive).forEach(System.out::println);
    }

    public void deleteStudent(EntityDao<Student> dao, Scanner scanner) {
        // nie da się usunąć rekordu po id (bezpośrednio z sesji)
        System.out.println("Podaj parametry: Identyfikator");
        Long id = Long.valueOf(scanner.nextLine());

        Optional<Student> studentOptional = dao.findById(Student.class, id);   // szukamy studenta
        if(studentOptional.isPresent()) {                       // jeśli uda się go odnaleźć
            Student student = studentOptional.get();            // wyciągamy studenta z Optional (Box, opakowanie)
            dao.delete(student);                                // przekazujemy do usunięcia
        }
    }

}
