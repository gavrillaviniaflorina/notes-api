package com.example.notesapi.repository;

import com.example.notesapi.model.Note;
import com.example.notesapi.model.Person;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note,Long> {

    @Query(value = "select * from note where person_id like ?1",nativeQuery = true)
    List<Note> allNotesOfAPerson(Long person_id);
}
