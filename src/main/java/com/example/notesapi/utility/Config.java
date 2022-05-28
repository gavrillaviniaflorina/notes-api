package com.example.notesapi.utility;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.Column;

@Component
public class Config {


    @Bean
    public ModelMapper modelMapper(){

        return  new ModelMapper();
    }

}
