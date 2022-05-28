package com.example.notesapi;

import com.example.notesapi.dto.NoteDto;
import com.example.notesapi.model.Note;
import com.example.notesapi.model.Person;
import com.example.notesapi.repository.NoteRepository;
import com.example.notesapi.repository.PersonRepository;
import com.example.notesapi.service.NoteService;
import com.example.notesapi.service.PersonService;
import com.github.javafaker.Faker;
import org.aspectj.weaver.ast.Not;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.management.MXBean;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class NotesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotesApiApplication.class, args);


    }

    @Bean
    CommandLineRunner commandLineRunner(
            PersonRepository personRepository, NoteRepository noteRepository
    ){
        return args->{

            Faker faker=new Faker();


//            for(int i=0;i<10;i++) {
//
//                String firstName=faker.name().firstName();
//                String lastName=faker.name().lastName();
//                String email=String.format("%s.%s@mycode.edu",firstName,lastName);
//
//
//
//                Person person = new Person( firstName+" "+lastName,email,faker.book().title());
//
//                personRepository.save(person);
//
//            }

            Person person=personRepository.findById(30L).get();


            for(int i=0;i<10;i++){

                LocalDate date=LocalDate.of(faker.number().numberBetween(2010,2022),faker.number().numberBetween(1,12),faker.number().numberBetween(1,30));
                Note note =new Note(faker.book().publisher(), faker.book().title(),date);

                person.addNote(note);

            }

            personRepository.save(person);


         //   System.out.println(personRepository.findPersonByEmail("Paulina.Heidenreich@mycode.edu"));


//            NoteDto noteDto=new NoteDto(1L,"TEST","TEST1",  LocalDate.of(2021,8,12));
//            PersonService personService=new PersonService( personRepository,new ModelMapper(),noteRepository);
//            personService.deletePerson(44L);
//            personService.addNote(noteDto);


//            NoteService noteService=new NoteService(noteRepository);
//            List<Note> notes= noteService.getAll();
//
//            System.out.println(notes.size());

           // PersonService personService=new PersonService(personRepository,new ModelMapper(), noteRepository);

           // System.out.println(personService.searchPerson("Effie.Fay@mycode.edu","For a Breath I Tarry"));

       };
    }



}
