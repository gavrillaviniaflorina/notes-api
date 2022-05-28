package com.example.notesapi.web;

import com.example.notesapi.dto.LoginDto;
import com.example.notesapi.dto.NoteDto;
import com.example.notesapi.model.Note;
import com.example.notesapi.model.Person;
import com.example.notesapi.repository.PersonRepository;
import com.example.notesapi.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RequestMapping("api/v1/persons")
@RestController
@CrossOrigin
public class PersonController {



    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<Person>> getPersons(){
        return new ResponseEntity<>(this.personService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Optional<Person>> validPerson(@RequestBody LoginDto loginDto) {

        return new ResponseEntity<>(this.personService.searchPerson(loginDto.getEmail(), loginDto.getPassword()),HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Person> addPerson(@RequestBody Person person){
       this.personService.addPerson(person);
       return  new ResponseEntity<>(person,HttpStatus.OK);
    }



    @DeleteMapping("delete/{id}")

    @ResponseStatus(value = HttpStatus.OK)
    public void  deletePerson(@PathVariable Long id){

        this.personService.deletePerson(id);

    }

    @PutMapping("update")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person){

        this.personService.updatePerson(person);

        return new ResponseEntity<>(person,HttpStatus.OK);
    }

    @PostMapping("/addNote")
    public ResponseEntity<NoteDto> addNote(@RequestBody NoteDto note){
        this.personService.addNote(note);
        return new ResponseEntity<>(note,HttpStatus.OK);
    }

    @DeleteMapping("deleteNote/{id}")

    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id){
        this.personService.deleteNote(id);


    }

    @PostMapping("getPersonId")
    public ResponseEntity<Long> getPersonId(@RequestBody LoginDto loginDto){

        return new ResponseEntity<>(this.personService.getActualPersonId(loginDto.getEmail(), loginDto.getPassword()),HttpStatus.OK);
    }


}
