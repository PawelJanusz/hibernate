import com.sda.javawro27.hibernate.controller.GradeController;
import com.sda.javawro27.hibernate.controller.StudentController;
import com.sda.javawro27.hibernate.controller.TeacherController;
import com.sda.javawro27.hibernate.dao.StudentDao;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        StudentController student = new StudentController();
        TeacherController teacher = new TeacherController();
        GradeController grade = new GradeController();

        Scanner scanner = new Scanner(System.in);

        String komenda;
        do{
            // https://pl.spoj.com/
            System.out.println("Podaj komendę [add/list/delete/update/byAge/addgrade/listgrade/delgrade/bybeh/addteacher/delteacher/quit]");
            komenda = scanner.nextLine();

            if(komenda.equalsIgnoreCase("add")){
                student.addStudents(scanner);
            }else if(komenda.equalsIgnoreCase("list")){
                student.listStudents();
            }else if(komenda.equalsIgnoreCase("delete")){
                student.deleteStudent(scanner);
            }else if(komenda.equalsIgnoreCase("update")){
                student.updateStudent(scanner);
            }else if(komenda.equalsIgnoreCase("byAge")){
                student.findByAge(new StudentDao(), scanner);
            }else if (komenda.equalsIgnoreCase("bybeh")){
                student.findByBehaviourAndAlive(new StudentDao(), scanner);
            }else if (komenda.equalsIgnoreCase("addgrade")) {
                grade.addGradeToStudent(scanner);
            }else if (komenda.equalsIgnoreCase("delgrade")) {
                grade.delGrade(scanner);
            }else if (komenda.equalsIgnoreCase("listgrades")) {
                grade.listStudentGrades(scanner);
            }else if (komenda.equalsIgnoreCase("changegrade")){
                // todo: zrobić później, można edytować przedmiot i wartość,
                // żeby zaobserwować zachowanie @UpdateTimeStamp
            }else if(komenda.equalsIgnoreCase("addteacher")){
                teacher.addTeacher(scanner);
            }else if (komenda.equalsIgnoreCase("connectteacher")){
                teacher.connectTeacher(scanner);
            }else if (komenda.equalsIgnoreCase("liststudents")){
                student.listStudents(scanner); // możliwość pobrania studentów wybranego nauczyciela
            }else if (komenda.equalsIgnoreCase("bylastname")){
                student.findByLastName(scanner);
            }
        }while (!komenda.equalsIgnoreCase("quit"));
    }
}
