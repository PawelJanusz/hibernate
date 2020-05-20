package com.sda.javawro27.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

// ta klasa załaduje konfigurację bazodanową
public class HibernateUtil {

    private static final SessionFactory ourSessionFactory;

    // blok statyczny - fragment kodu który wykonuje się 1 raz przy starcie klasy
    static {
        try {
            System.out.println("Konfiguruję hibernate");

            // ta klasa odwołuje sie do pliku konfiguracyjnego hibernate w formacie podanym w resources
            Configuration configuration = new Configuration();
            configuration.configure("/hibernate.cfg.xml");
            ourSessionFactory = configuration.buildSessionFactory();

        } catch (HibernateException he){
            System.out.println(he.getMessage());
//            System.exit(376); // liczba która jest kodem błedu - jeśli zobaczymy liczbę znaczy że jest to bład hibernate
            throw he;
        }
    }

    // wygenerowany getter

    public static SessionFactory getOurSessionFactory() {
        return ourSessionFactory;
    }
}
