package com.sda.javawro27.hibernate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
    @Entity // jest to klasa bazodanowa
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class Student {
    // STUDENT 1 2 5 7 8
    // GRADE 3 4 6 9

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        // identity - identyfikator pochodzi z bazy danych
        // sequence - licznik identyfikatorów pochodzi z hibernate, wszystkie klasy posiadają wspólny licznik
        // table - licznik identyfikatorów pochodzi z hibernate i posiada oddzielny dla każdej tabeli
        private Long id;

        // POJO plain old java object
         //   - pola muszą mieć gettery i settery
        //    - pusty konstruktor
        private String firstName;
        private String lastName;
        private double height;
        private int age;
        private boolean alive; // nie isAlive

        @Enumerated(value = EnumType.STRING)
        private Behaviour behaviour;
        // lombok jeśli wygeneruje metodę na podstawie pola isAlive, to nazywać się będzie "isIsAlive"
        // isAlive
    }


