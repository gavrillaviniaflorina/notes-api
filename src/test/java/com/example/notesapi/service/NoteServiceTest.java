package com.example.notesapi.service;

import com.example.notesapi.dto.NoteDto;
import com.example.notesapi.exceptions.NoteNotFoundException;
import com.example.notesapi.model.Note;
import com.example.notesapi.model.Person;
import com.example.notesapi.repository.NoteRepository;
import com.example.notesapi.repository.PersonRepository;
import com.github.javafaker.Faker;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;


class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private PersonRepository personRepository;

    @Captor
    private ArgumentCaptor<Note> notesArgumentCaptor;



    private NoteService underTest;

    @BeforeEach
     void setup(){
        MockitoAnnotations.openMocks(this);
        underTest=new NoteService(noteRepository);
    }

    @Test
    void itShouldGetAllNotes(){

        List<Note> notes=new ArrayList<>();

        for (int i=0;i<10;i++){
            notes.add(new Note());
        }

        doReturn(notes).when(noteRepository).findAll();
        assertThat(underTest.getAll()).isEqualTo(notes);
    }

    @Test
    void itShouldUpdateNote(){

        Faker faker=new Faker();

        LocalDate date= LocalDate.of(faker.number().numberBetween(2010,2022),faker.number().numberBetween(1,12),faker.number().numberBetween(1,30));

        Note note=new Note(faker.book().title(),faker.book().publisher(),date);
        note.setId(1L);

        doReturn(true).when(noteRepository).existsById(1L);
        doReturn(Optional.of(note)).when(noteRepository).findById(1L);

        underTest.updateNote(note);

        then(noteRepository).should().save(notesArgumentCaptor.capture());

        Note noteCapture=notesArgumentCaptor.getValue();

        assertThat(note).isEqualTo(noteCapture);

    }

    @Test
    void itShouldNotUpdateNote(){
        Faker faker=new Faker();

        LocalDate date= LocalDate.of(faker.number().numberBetween(2010,2022),faker.number().numberBetween(1,12),faker.number().numberBetween(1,30));

        Note note=new Note(faker.book().title(),faker.book().publisher(),date);
        note.setId(1L);

        doReturn(false).when(noteRepository).existsById(1L);

        assertThatThrownBy(()->underTest.updateNote(note)).isInstanceOf(NoteNotFoundException.class).hasMessageContaining("Note not found");
    }




}