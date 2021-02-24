package com.sda.javawro27.hibernate.model;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

//POJO - Plain old java object
//     -pola muszą mieć gettery i settery
//     -pusty konstruktor

//@Data  getter, setter, equals, hashcode, toString
    //@NoArgsConstructor

    @Entity // jest to klasa bazodanowa, trzeba dopisać tą klase w pliku cfg.xml w tagu mapping
    @Table(name = "student")
    @Builder
    @Getter
    @Setter
    @EqualsAndHashCode
    @ToString
    @RequiredArgsConstructor // kontruktor z wymaganymi parametrami - wymagane pola to pola finalne
    @AllArgsConstructor
    public class Student implements LastNameSearchable{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY) //pozwala na autoincrement
        private Long id;
        // identity - identyfikator pochodzi z bazy danych

        // sequence - licznik identyfikatorów pochodzi z hibernate, wszystkie klasy posiadają wspólny licznik
        // STUDENT 1 2 5 7 8
        // GRADE 3 4 6 9

        // table - licznik identyfikatorów pochodzi z hibernate i posiada oddzielny dla każdej tabeli


        // POJO plain old java object
        //   - pola muszą mieć gettery i settery
        //    - pusty konstruktor

        //@Column (name = "first_name")
        // jeżeli nie wpisze adnotacji column to nazwy kolumn będą takie same jak nazwa pól
        private String firstName;
        private String lastName;

        private double height;
        private int age;

        @Column(columnDefinition = "default 0")
        private boolean alive; // nie isAlive

        @Enumerated(value = EnumType.STRING) // wyświetlenie w bazie danych Stringa a nie liczby
        private Behaviour behaviour;
        // lombok jeśli wygeneruje metodę na podstawie pola isAlive, to nazywać się będzie "isIsAlive"
        // isAlive

        //baza danych domyślnie traktuje symetryczne odwołania (ManyToOne, OneToMany)
        //jako niezależne powiązania
        //tutaj utworzy się dodatkowa tabela z relacją
        @OneToMany(mappedBy = "studentRef", fetch = FetchType.EAGER)
        private Set<Grade> gradeList;

        // Formula oznacza nam pole jako NIE-KOLUMNE i obliczenie wartości tego pola musi być zawarte w "value"
        @Formula(value = "(select avg(grade.gradeValue) from grade where grade.studentRef_id = id)")
        private Double average; // przy typie złożonym "Double" liczymy także średnią z 0 liczb = N/A;
                                //przy typie prostym "double" przy ilości liczb: 0 - będzie exception

        // nie może istnieć relacja EAGER z Listą więcej niż jeden raz w modelu

        // 1:1 (OneToOne) - osoba ma tylko jeden adres - oneToOne - lub złączyć obiekty
        // 1:n (OneToMany) -
        // n:1 (ManyToOne)
        // n:n (ManyToMany)

        @ManyToMany(fetch = FetchType.EAGER)
        @EqualsAndHashCode.Exclude
        @ToString.Exclude // powstrzymanie przed rekurencyjnym zapętleniem
        private Set<Teacher> teacherSet;
    }


