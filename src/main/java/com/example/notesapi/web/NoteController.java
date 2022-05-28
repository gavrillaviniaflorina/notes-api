package com.example.notesapi.web;


import com.example.notesapi.model.Note;
import com.example.notesapi.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/notes")
@RestController
@CrossOrigin
public class NoteController {

    private NoteService noteService;

    public NoteController (NoteService noteService){
        this.noteService=noteService;
    }

    @GetMapping
    public ResponseEntity<List<Note>> getNotes(){
       return new ResponseEntity<>(this.noteService.getAll(), HttpStatus.OK);
    }

    @PutMapping("update")

    public ResponseEntity<Note> updateNote(@RequestBody Note note){
        this.noteService.updateNote(note);
        return new ResponseEntity<>(note,HttpStatus.OK);
    }


    @GetMapping("allNotesOfAPerson/{idPerson}")
    public ResponseEntity<List<Note>> getNotesOfAPerson(@PathVariable Long idPerson){

        return  new ResponseEntity<>(this.noteService.getNotesOfAPerson(idPerson), HttpStatus.OK);
    }

    @GetMapping("ascending/{idPerson}")
    public ResponseEntity<List<Note>> ascending(@PathVariable Long idPerson){
        return new ResponseEntity<>(this.noteService.sortAscending(idPerson),HttpStatus.OK);
    }

    @GetMapping("descending/{idPerson}")
    public ResponseEntity<List<Note>> descending(@PathVariable Long idPerson){
        return new ResponseEntity<>(this.noteService.sortDescending(idPerson),HttpStatus.OK);
   }

   @GetMapping("alphabetical/{idPerson}")
    public ResponseEntity<List<Note>> alphabetical(@PathVariable Long idPerson){
        return new ResponseEntity<>(this.noteService.sortAlphabetical(idPerson),HttpStatus.OK);
   }
}
