package com.example.notesapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;



@Table(name = "note")
@Entity(name = "Note")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Note {
    @Id
    @SequenceGenerator(
            name="notes_sequence",
            sequenceName = "notes_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "notes_sequence"
    )
    @Column(
            name="id"
    )

    private Long id;

    @Column(
            name="text",
            columnDefinition = "TEXT"
    )
    private String text;

    @Column(
            name="title",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String title;

    @Column(
            name="date",
            nullable = false,
            columnDefinition  ="DATE"
    )

    private LocalDate date;

    public Note(String text, String title, LocalDate date) {
        this.text = text;
        this.title = title;
        this.date = date;

    }

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name="person_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name="person_id_fk"
            )

    )
    @JsonBackReference
    private Person person;

    @Override
    public boolean equals(Object obj){

        Note note=(Note) obj;
        return note.id==this.id;
    }


}

