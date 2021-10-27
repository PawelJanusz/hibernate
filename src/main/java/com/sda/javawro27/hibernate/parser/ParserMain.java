package com.sda.javawro27.hibernate.parser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ParserMain {

    public static void main(String[] args) {

        String parser = "44 168 66";
        String[] split = parser.split(" ");

        int age = Integer.parseInt(split [0]);
        int height = Integer.parseInt(split [1]);
        int weight = Integer.parseInt(split [2]);

        System.out.println("Wiek: " + age);
        System.out.println("Wzrost: " + height);
        System.out.println("Waga: " + weight);

        char[] signs = parser.toCharArray();

        System.out.println(signs);



    }
}
