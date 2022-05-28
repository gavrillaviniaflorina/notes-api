package com.example.notesapi.service;


import com.example.notesapi.exceptions.NoteNotFoundException;
import com.example.notesapi.model.Note;
import com.example.notesapi.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository){
        this.noteRepository=noteRepository;

    }


    public List<Note> getAll(){
        return noteRepository.findAll();
    }

    public List<Note> sortAscending(Long id){


        List<Note> notes=noteRepository.allNotesOfAPerson(id).stream().sorted(Comparator.comparing(Note::getDate)).collect(Collectors.toList());
        return notes;
    }

    public List<Note> sortDescending(Long id){
        List<Note> notes=noteRepository.allNotesOfAPerson(id).stream().sorted(Comparator.comparing(Note::getDate).reversed()).collect(Collectors.toList());
        return  notes;
    }

    public List<Note> sortAlphabetical(Long id){
        List<Note> notes=noteRepository.allNotesOfAPerson(id).stream().sorted(Comparator.comparing(Note::getTitle)).collect(Collectors.toList());
        return  notes;
    }

    public List<Note> getNotesOfAPerson(Long person_id){
        return  noteRepository.allNotesOfAPerson(person_id);

    }

    public void updateNote(Note updatedNote){


        Boolean updatedId=this.noteRepository.existsById(updatedNote.getId());

        if(!updatedId){
            throw new NoteNotFoundException(
                    "Note not found"
            );
        }

        this.noteRepository.findById(updatedNote.getId()).map(note -> {

            note.setDate(updatedNote.getDate());
            note.setText(updatedNote.getText());
            note.setTitle(updatedNote.getTitle());

            return noteRepository.save(note);
        });
    }




}
