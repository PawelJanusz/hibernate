import com.sda.javawro27.hibernate.dao.EntityDao;
import com.sda.javawro27.hibernate.dao.StudentDao;
import com.sda.javawro27.hibernate.dao.TeacherDao;
import com.sda.javawro27.hibernate.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        
        // trzeba mieć stworzoną bazę i tabelę

        Scanner scanner = new Scanner(System.in);

        String komenda;
        do{
            // https://pl.spoj.com/
            System.out.println("Podaj komendę [add/list/delete/update/byAge/addgrade/bybeh/addteacher/delteacher/quit]");
            komenda = scanner.nextLine();

            if(komenda.equalsIgnoreCase("add")){
                addStudents(scanner);
            }else if(komenda.equalsIgnoreCase("list")){
                listStudents();
            }else if(komenda.equalsIgnoreCase("delete")){
                deleteStudent(scanner);
            }else if(komenda.equalsIgnoreCase("update")){
                updateStudent(scanner);
            }else if(komenda.equalsIgnoreCase("byAge")){
                findByAge(new StudentDao(), scanner);
            }else if (komenda.equalsIgnoreCase("bybeh")){
                findByBehaviourAndAlive(new StudentDao(), scanner);
            }else if (komenda.equalsIgnoreCase("addgrade")) {
                addGradeToStudent(scanner);
            }else if (komenda.equalsIgnoreCase("delgrade")) {
                delGrade(scanner);
            }else if (komenda.equalsIgnoreCase("listgrades")) {
                listStudentGrades(scanner);
            }else if (komenda.equalsIgnoreCase("changegrade")){
                // todo: zrobić później, można edytować przedmiot i wartość,
                // żeby zaobserwować zachowanie @UpdateTimeStamp
            }else if(komenda.equalsIgnoreCase("addteacher")){
                addTeacher(scanner);
            }else if (komenda.equalsIgnoreCase("connectteacher")){
                connectTeacher(scanner);
            }else if (komenda.equalsIgnoreCase("liststudents")){
                listStudents(scanner); // możliwość pobrania studentów wybranego nauczyciela
            }else if (komenda.equalsIgnoreCase("bylastname")){
                findByLastName(scanner);
            }
        }while (!komenda.equalsIgnoreCase("quit"));
    }

    private static void findByLastName(Scanner scanner){
        System.out.println("Podaj parametry: nazwisko");
        String last = scanner.nextLine();

        StudentDao studentDao = new StudentDao();
        List<Student> studentList = studentDao.findByLastName(Student.class, last);
        List<Teacher> teacherList = studentDao.findByLastName(Teacher.class, last);
    }

    private static void listStudents(Scanner scanner){
        System.out.println("Podaj parametry: Identyfikator nauczyciela");
        Long idT = Long.valueOf(scanner.nextLine());

        new TeacherDao().findStudents(idT)
                .forEach(System.out::println);
    }

    private static void connectTeacher(Scanner scanner){
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

    private static void addTeacher(Scanner scanner) {
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

    private static void listStudentGrades(Scanner scanner) {
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

    private static void delGrade(Scanner scanner) {
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

    private static void addGradeToStudent(Scanner scanner) {
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

    private static void findByAge(StudentDao dao, Scanner scanner){
        System.out.println("Podaj parametry: AgeFrom, AgeTo");
        String linia = scanner.nextLine();
        int ageFrom = Integer.valueOf(linia.split(" ")[0]);
        int ageTo = Integer.valueOf(linia.split(" ")[1]);

        System.out.println("Znalezione rekordy: ");
        dao.findByAgeBetween(ageFrom, ageTo).forEach(System.out::println);
    }

    private static void findByBehaviourAndAlive(StudentDao dao, Scanner scanner){
        System.out.println("Podaj parametry: Behaviour, Alive");
        String linia = scanner.nextLine();

        Behaviour behaviour = Behaviour.valueOf(linia.split(" ")[0]);
        boolean alive = Boolean.parseBoolean(linia.split(" ")[1]);

        System.out.println("Znalezione rekordy: ");
        dao.findByBehaviourAndAlive(behaviour, alive).forEach(System.out::println);
    }

    private static void deleteStudent(Scanner scanner) {
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


    private static void listStudents(){
        System.out.println("Lista studentów");
        new StudentDao().getAll().stream()
                .forEach(System.out::println);
    }

    private static void updateStudent(Scanner scanner) {
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


    private static void deleteStudent(StudentDao dao, Scanner scanner) {
        // nie da się usunąć rekordu po id (bezpośrednio z sesji)
        System.out.println("Podaj parametry: Identyfikator");
        Long id = Long.valueOf(scanner.nextLine());

    Optional<Student> studentOptional = dao.findById(id);   // szukamy studenta
        if(studentOptional.isPresent()) {                       // jeśli uda się go odnaleźć
        Student student = studentOptional.get();            // wyciągamy studenta z Optional (Box, opakowanie)
        dao.delete(student);                                // przekazujemy do usunięcia
    }
}

    private static void addStudents(Scanner scanner) {
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
}
