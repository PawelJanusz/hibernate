import com.sda.javawro27.hibernate.controller.GradeController;
import com.sda.javawro27.hibernate.controller.StudentController;
import com.sda.javawro27.hibernate.controller.TeacherController;
import com.sda.javawro27.hibernate.dao.StudentDao;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        mainMenu();

    }
    private static Scanner scanner = new Scanner(System.in);
    private static String command;

    private static void mainMenu(){
        do {
            System.out.println("Choose option number or option: \n" +
                    "1.Student \n" +
                    "2.Teacher \n" +
                    "3.Grade \n" +
                    "Quit");
            command = scanner.nextLine();
            if (command.equals("1")){
                studentMenu();
            }
            if (command.equals("2")){
                teacherMenu();
            }
            if (command.equals("3")){
                gradeMenu();
            }
        }while (!command.equalsIgnoreCase("quit"));
    }

    private static void studentMenu(){
        StudentController student = new StudentController();
        StudentDao studentDao = new StudentDao();

        do {
            System.out.println("Choose option number or option: \n" +
                    "STUDENT \n" +
                    "1.Add \n" +
                    "2.List \n" +
                    "3.List students of teacher \n" +
                    "4.Update \n" +
                    "5.Delete \n" +
                    "6.Find by age \n" +
                    "7.Find by behaviour and alive \n" +
                    "8.Find by last name \n" +
                    "Back \n" +
                    "Quit");
            command = scanner.nextLine();
            if (command.equals("1")){
                student.addStudents(scanner);
            }
            if (command.equals("2")){
                student.listStudents();
            }
            if (command.equals("3")){
                student.listStudentsOfTeacher(scanner);
            }
            if (command.equals("4")){
                student.updateStudent(scanner);
            }
            if (command.equals("5")){
                student.deleteStudent(scanner);
            }
            if (command.equals("6")){
                student.findByAge(studentDao, scanner);
            }
            if (command.equals("7")){
                student.findByBehaviourAndAlive(studentDao, scanner);
            }
            if (command.equals("8")){
                student.findByLastName(scanner);
            }
            if (command.equalsIgnoreCase("back")){
                mainMenu();
            }
        }while (!command.equalsIgnoreCase("quit"));
    }

    private static void gradeMenu(){
        GradeController grade = new GradeController();

        do {
            System.out.println("Choose option number or option: \n" +
                    "GRADE \n" +
                    "1.Add grade to student\n" +
                    "2.List student grades\n" +
                    "3.Delete grade \n" +
                    "Back \n" +
                    "Quit");
            command = scanner.nextLine();
            if (command.equals("1")){
                grade.addGradeToStudent(scanner);
            }
            if (command.equals("2")){
                grade.listStudentGrades(scanner);
            }
            if (command.equals("3")){
                grade.delGrade(scanner);
            }
            if (command.equalsIgnoreCase("back")){
                mainMenu();
            }
        }while (!command.equalsIgnoreCase("quit"));
    }

    private static void teacherMenu(){
        TeacherController teacher = new TeacherController();

        do {
            System.out.println("Choose option number or option: \n" +
                    "TEACHER \n" +
                    "1.Add \n" +
                    "2.Connect teacher and student\n" +
                    "Back \n" +
                    "Quit");
            command = scanner.nextLine();
            if (command.equals("1")){
                teacher.addTeacher(scanner);
            }
            if (command.equals("2")){
                teacher.connectTeacher(scanner);
            }
            if (command.equalsIgnoreCase("back")){
                mainMenu();
            }
        }while (!command.equalsIgnoreCase("quit"));
    }
}
