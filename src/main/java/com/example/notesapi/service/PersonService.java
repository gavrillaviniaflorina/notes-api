package com.example.notesapi.service;

import com.example.notesapi.dto.NoteDto;
import com.example.notesapi.exceptions.BadRequestException;
import com.example.notesapi.exceptions.InvalidEmailOrPasswordException;
import com.example.notesapi.exceptions.NoteNotFoundException;
import com.example.notesapi.exceptions.PersonNotFoundException;
import com.example.notesapi.model.Note;
import com.example.notesapi.model.Person;
import com.example.notesapi.repository.NoteRepository;
import com.example.notesapi.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private PersonRepository personRepository;
    private NoteRepository noteRepository;
    private ModelMapper modelMapper;

    public PersonService(PersonRepository personRepository, ModelMapper modelMapper,NoteRepository noteRepository) {
        this.personRepository = personRepository;
        this.modelMapper=modelMapper;
        this.noteRepository=noteRepository;
    }

    public List<Person> getAll(){
        return  this.personRepository.findAll();
    }

    public void addPerson(Person person){

        Boolean existsEmail=this.personRepository.selectedEmailExists(person.getEmail()).isPresent();

        if(existsEmail){
            throw  new BadRequestException(
                    "Email "+person.getEmail()+" taken"
             );
        }
       personRepository.save(new Person(person.getName(),person.getEmail(),person.getPassword()));
    }


    public void deletePerson(Long id){
        Boolean existsId=this.personRepository.selectedIdExists(id).isEmpty();

        if(existsId){
            throw new PersonNotFoundException(
                    "Person not found"
            );
        }
        personRepository.deleteById(id);
    }

    public void updatePerson(Person updatePerson){


        Boolean updateId=this.personRepository.selectedIdExists(updatePerson.getId()).isEmpty();

        if(updateId){
            throw new PersonNotFoundException(
                    "Person not found"
            );
        }

        this.personRepository.findById(updatePerson.getId()).map(person->{
            person.setEmail(updatePerson.getEmail());
            person.setName(updatePerson.getName());
            person.setPassword(updatePerson.getPassword());

            return personRepository.save(person);
        });



    }

    public void addNote(NoteDto noteDto){
        Optional<Person> person=this.personRepository.selectedIdExists(noteDto.getIdPerson());


        if(person.isPresent()){

            Person person1=person.get();

            modelMapper.getConfiguration()
                    .setMatchingStrategy(MatchingStrategies.LOOSE);

            Note note= new Note();
            modelMapper.map(noteDto,note);
            person1.addNote(note);
            personRepository.save(person.get());

            //return noteDto;

        }else{

            throw new PersonNotFoundException(
                    "Person not found"
            );
        }

    }

   public void deleteNote(Long id){

        Optional<Note> note=noteRepository.findById(id);
       if (note.isPresent()) {


           noteRepository.deleteById(id);


       }else{
           throw new NoteNotFoundException(
                   "Note not found"
           );
       }

   }

   public Optional<Person> searchPerson(String email,String password){


        Optional<Person> person=personRepository.selectedEmailExists(email);
        if(person.isEmpty()){
            throw new InvalidEmailOrPasswordException("Invalid email or password");
        }

        return person;

   }

   public Long  getActualPersonId(String email, String password){
       Optional<Person> person=personRepository.selectedEmailExists(email);
       if(person.isEmpty()){
           throw new InvalidEmailOrPasswordException("Invalid email or password");
       }

       return person.get().getId();
   }




}
