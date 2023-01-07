package com.example.sproject;

import com.example.sproject.Services.FileServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SprojectApplication implements CommandLineRunner {
    FileServiceImpl fileService;
    @Autowired
    public SprojectApplication(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SprojectApplication.class, args);
    }
    @Override
    public void run(String... arg) throws Exception {
//    storageService.deleteAll();
        fileService.init();
    }

    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
