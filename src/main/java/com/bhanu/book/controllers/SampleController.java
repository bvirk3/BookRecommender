package com.bhanu.book.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.bhanu.book.client.search.GenreSearch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

@RestController
public class SampleController {
	@RequestMapping("/hello/{genre}")
	public String sample(@PathVariable String genre){
		JacksonFactory factory = new JacksonFactory();
		GenreSearch genreSearch = new GenreSearch();
		try {
			return genreSearch.getBooksByGenre(genre, factory, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Exception :(";
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Exception :(";
		}
		
	}
}
