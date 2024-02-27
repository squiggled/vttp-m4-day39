package com.example.day39pdfws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.day39pdfws.services.SearchService;

@SpringBootApplication
public class Day39pdfwsApplication implements CommandLineRunner{

	@Autowired
	SearchService searchService;
	public static void main(String[] args) {
		SpringApplication.run(Day39pdfwsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// searchService.getGifs("mushroom");
	}

}
