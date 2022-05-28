package com.example.notesapi.repository;

import com.example.notesapi.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

@Query(value = "select * from person where email like ?1",nativeQuery = true)
Optional<Person> selectedEmailExists(String email);

@Query(value="select * from person where id = ?1 ", nativeQuery = true)
    Optional<Person> selectedIdExists(Long id);

}
