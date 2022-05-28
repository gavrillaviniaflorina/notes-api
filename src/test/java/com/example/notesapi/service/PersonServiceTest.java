package com.example.notesapi.service;

import com.example.notesapi.dto.NoteDto;
import com.example.notesapi.exceptions.BadRequestException;
import com.example.notesapi.exceptions.NoteNotFoundException;
import com.example.notesapi.exceptions.PersonNotFoundException;
import com.example.notesapi.model.Note;
import com.example.notesapi.model.Person;
import com.example.notesapi.repository.NoteRepository;
import com.example.notesapi.repository.PersonRepository;
import com.github.javafaker.Faker;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.aspectj.weaver.ast.Instanceof;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.doReturn;
import static org.mockito.BDDMockito.then;

class PersonServiceTest {


    @Mock
    private PersonRepository personRepository;
    @Mock
    private NoteRepository noteRepository;

    @Captor
    private ArgumentCaptor<Person> personArgumentCaptor;

    @Captor
    private ArgumentCaptor<Integer> noteIdArgumentCaptor;


  //  @InjectMocks
    private PersonService underTest;

    private ModelMapper modelMapper=new ModelMapper();

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        underTest=new PersonService(personRepository, modelMapper, noteRepository);
    }

    @Test
    void itShouldGetAllPersons(){


        List<Person>persons= new ArrayList<>();


        for(int i=0;i<10;i++){
            persons.add(new Person());
        }

        doReturn(persons).when(personRepository).findAll();


        assertThat(underTest.getAll()).isEqualTo(persons);

    }

    @Test
    void ItShouldDeletePerson(){

        Faker faker=new Faker();
        Person person=new Person("lavinia","lavinia.Fay@mycode.edu","ceva");
        person.setId(1L);



        doReturn(Optional.of(person)).when(personRepository).selectedIdExists(1l);

        underTest.deletePerson(person.getId());

        BDDMockito.then(personRepository).should().deleteById(person.getId());

    }

    @Test
    void ItShouldThrowAnExceptonWhenDeleting(){

        Faker faker=new Faker();
        Person person=new Person("lavinia","lavinia.Fay@mycode.edu","ceva");
        person.setId(1L);


        doReturn(Optional.empty()).when(personRepository).selectedIdExists(1l);

        assertThatThrownBy(()->underTest.deletePerson(person.getId())).isInstanceOf(PersonNotFoundException.class).hasMessageContaining("Person not found");
    }

    @Test
    void ItShouldUpdatePerson(){
        Faker faker=new Faker();

        Person newPerson=new Person("lavinia","lavinia.Fay@mycode.edu","ceva");
        newPerson.setId(1L);

        doReturn(Optional.of(newPerson)).when(personRepository).selectedIdExists(1L);
        doReturn(Optional.of(newPerson)).when(personRepository).findById(1L);
        underTest.updatePerson(newPerson);
        then(personRepository).should().save(personArgumentCaptor.capture());

        Person personCapture=personArgumentCaptor.getValue();

        assertThat(personCapture).isEqualTo(newPerson);


    }

    @Test
    void itShouldNotUpdatePerson(){
        Faker faker=new Faker();


        Person newPerson=new Person("lavinia","lavinia.Fay@mycode.edu","ceva");
        newPerson.setId(1L);

        doReturn(Optional.empty()).when(personRepository).selectedIdExists(1L);
        doReturn(Optional.empty()).when(personRepository).findById(1l);
        assertThatThrownBy(()->underTest.updatePerson(newPerson)).isInstanceOf(PersonNotFoundException.class).hasMessageContaining("Person not found");

    }

    @Test
    void itShouldAddNote(){
        Faker faker=new Faker();

        Person person=new Person("lavinia","lavinia.Fay@mycode.edu","ceva");

        person.setId(1L);

        doReturn(Optional.of(person)).when(personRepository).selectedIdExists(1L);

        LocalDate date= LocalDate.of(faker.number().numberBetween(2010,2022),faker.number().numberBetween(1,12),faker.number().numberBetween(1,30));

        NoteDto noteDto=new NoteDto(person.getId(),faker.book().title(),faker.book().publisher(),date);

       underTest.addNote(noteDto);

       then(personRepository).should().save(personArgumentCaptor.capture());

       Person person1=personArgumentCaptor.getValue();

       assertThat(person1).isEqualTo(person);

    }

    @Test
    void itShouldNotSaveNote(){

        Faker faker=new Faker();

        Person person=new Person("lavinia","lavinia.Fay@mycode.edu","ceva");

        person.setId(1L);

        LocalDate date= LocalDate.of(faker.number().numberBetween(2010,2022),faker.number().numberBetween(1,12),faker.number().numberBetween(1,30));

        NoteDto noteDto=new NoteDto(person.getId(),faker.book().title(),faker.book().publisher(),date);


        doReturn(Optional.empty()).when(personRepository).selectedIdExists(1L);

        assertThatThrownBy(()->underTest.addNote(noteDto)).isInstanceOf(PersonNotFoundException.class).hasMessageContaining("Person not found");

    }

    @Test
    void itShouldDeleteNote(){
        Faker faker=new Faker();

        Person person=new Person("lavinia","lavinia.Fay@mycode.edu","ceva");
        person.setId(1L);

        Long id=1L;

        LocalDate date= LocalDate.of(faker.number().numberBetween(2010,2022),faker.number().numberBetween(1,12),faker.number().numberBetween(1,30));

        Note note=new Note(faker.book().title(),faker.book().publisher(),date);

        note.setPerson(person);

        doReturn(Optional.of(note)).when(noteRepository).findById(id);

        doReturn(Optional.of(person)).when(personRepository).selectedIdExists(1L);


        underTest.deleteNote(id);

        then(personRepository).should().save(personArgumentCaptor.capture());

        Person person1=personArgumentCaptor.getValue();

        assertThat(person1).isEqualTo(person);
    }

    @Test
    void itShouldNotDeleteNote(){

        Faker faker=new Faker();

        Person person=new Person("lavinia","lavinia.Fay@mycode.edu","ceva");
        person.setId(1L);

        Long id=1L;

        LocalDate date= LocalDate.of(faker.number().numberBetween(2010,2022),faker.number().numberBetween(1,12),faker.number().numberBetween(1,30));

        Note note=new Note(faker.book().title(),faker.book().publisher(),date);

        note.setPerson(person);

        doReturn(Optional.empty()).when(noteRepository).findById(id);

        doReturn(Optional.of(person)).when(personRepository).selectedIdExists(1L);

       assertThatThrownBy(()->underTest.deleteNote(id)).isInstanceOf(NoteNotFoundException.class).hasMessageContaining("Note not found");

    }

    @Test
    void itShouldAddPerson(){
        Faker faker=new Faker();

        Person person=new Person("lavinia","lavinia.Fay@mycode.edu","ceva");

        doReturn(Optional.empty()).when(personRepository).selectedEmailExists(person.getEmail());

        underTest.addPerson(person);

        then(personRepository).should().save(personArgumentCaptor.capture());
        Person person1=personArgumentCaptor.getValue();
        assertThat(person1).isEqualTo(person);

    }

    @Test
    void itShouldNotSavePerson(){
        Faker faker=new Faker();

        Person person=new Person("lavinia","lavinia.Fay@mycode.edu","ceva");

        doReturn(Optional.of(person)).when(personRepository).selectedEmailExists(person.getEmail());

        assertThatThrownBy(()->underTest.addPerson(person)).isInstanceOf(BadRequestException.class).hasMessageContaining("Email "+person.getEmail()+" taken");

    }


}