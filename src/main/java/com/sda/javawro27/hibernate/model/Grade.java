package com.sda.javawro27.hibernate.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import javax.persistence.*;
import java.time.LocalDateTime;

//@Data
@Getter
@Setter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor // konstruktor z "wymaganymi" parametrami. (jeśli nie ma pól finalnych, to mówimy o domyślnym)

@Entity
@AllArgsConstructor
//@NoArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "DOUBLE CHECK (gradeValue < 6) CHECK (gradeValue > 0)")
    private double gradeValue;

    //kiedy ocena została wstawiona
    @CreationTimestamp // on_insert = now()
    private LocalDateTime created;

    //aktualizowane w momencie aktualizacji wpisu w bazie
    @UpdateTimestamp // on_insert = now()
    private LocalDateTime updated;

    @Enumerated(value = EnumType.STRING)
    private GradeSubject subject;

    @ManyToOne // relacja zwrotna, dopisanie relacji jest opcjonalne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Student studentRef;

    public Grade (double gradeValue, GradeSubject subject){
        this.gradeValue = gradeValue;
        this.subject = subject;
    }
}
