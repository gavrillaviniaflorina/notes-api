package com.example.notesapi.repository;

import com.example.notesapi.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestPropertySource(

        locations = "classpath:application-test.properties"
)
class PersonRepositoryTest {

    @Autowired
    private PersonRepository underTest;


    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfTheEmailExists(){
        String email="lavinia.Fay@mycode.edu";
        Person person=new Person("Lavinia Gavril","lavinia.Fay@mycode.edu","For a Breath I Tarry");
        underTest.save(person);
        Optional<Person> expect=underTest.selectedEmailExists(email);


        assertThat(expect).isNotEmpty();

    }

    @Test
    void itShouldCheckIfTheEmailDoesNotExist(){
        String email="lavinia.Fay@mycode.edu";
        Optional<Person> expect=underTest.selectedEmailExists(email);

        assertThat(expect).isEmpty();
    }


}