package com.example.notesapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {

    private Long idPerson;
    private String text;
    private String title;
    private LocalDate date;

}
