package com.example.notesapi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;


@Entity(name="Person")
@Table(name="person")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Person {

    @Id
    @SequenceGenerator(
            name="person_sequence",
            sequenceName = "person_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "person_sequence"
    )

    @Column(
            name="id"
    )

    private long id;

    @Column(
            name="name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NotEmpty
    private String name;


    @Column(
            name="email",
            nullable = false,
            unique = true
    )
    @Email
    @NotEmpty
    private String email;

    @Column(
            name="password",
            nullable = false


    )
    @NotEmpty
        private String password;


    @OneToMany(
            mappedBy = "person",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JsonManagedReference
    List<Note> notes=new ArrayList<>();

    public Person(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;

    }

    //add delete

    public void addNote(Note note){
        this.notes.add(note);
        note.setPerson(this);
    }

    public void deleteNote(Long id){


        Note note = new Note();
        note.setId(id);
        this.notes.remove(note);

    }





}