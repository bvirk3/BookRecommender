package org.bhanu.book.client.search;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;

import org.bhanu.book.client.Credentials;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.Books.Volumes.List;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volume.VolumeInfo;
import com.google.api.services.books.model.Volumes;

public class GenreSearch {
	public String getBooksByGenre(String genre, JacksonFactory jsFactory,
			String filter) throws IOException, GeneralSecurityException {
		final Books books = new Books.Builder(
				GoogleNetHttpTransport.newTrustedTransport(), jsFactory, null)
				.setApplicationName(Credentials.APP_NAME)
				.setGoogleClientRequestInitializer(
						new BooksRequestInitializer(Credentials.API_KEY))
				.build();
		List volumeList = books.volumes().list("subject:" + genre);
		volumeList.setFilter(filter);
		Volumes volumes = volumeList.execute();
		int count = 5;
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		JsonGenerator jsonGenerator = jsFactory.createJsonGenerator(
				arrayOutputStream, Charset.defaultCharset());
		jsonGenerator.writeStartObject();
		jsonGenerator.writeFieldName("booksByGenre");
		jsonGenerator.writeStartArray();
		
		for (Volume volume : volumes.getItems()) {
			count--;
			
			VolumeInfo volumeInfo = volume.getVolumeInfo();
			if (volumeInfo != null) {
				
				jsonGenerator.writeStartObject();
				jsonGenerator.writeFieldName("title");
				jsonGenerator.writeString(volumeInfo.getTitle());
				jsonGenerator.writeFieldName("authors");
				jsonGenerator.writeStartArray();
				
				java.util.List<String> authors = volumeInfo.getAuthors();
			      if (authors != null && !authors.isEmpty()) {
			        for (int i = 0; i < authors.size(); ++i) {
			          jsonGenerator.writeString(authors.get(i));
			          
			        }
			        
			      }
			    
			    jsonGenerator.writeEndArray(); 
			    jsonGenerator.writeFieldName("link");
			    jsonGenerator.writeString(volumeInfo.getInfoLink());
				jsonGenerator.writeEndObject();
				if(count == 0)
					break;
				
			}
			
			
			
		}
		jsonGenerator.writeEndArray();
		jsonGenerator.writeEndObject();
		jsonGenerator.close();
		
		return arrayOutputStream.toString();
	}
	public static void main(String[] args) {
		GenreSearch obj = new GenreSearch();
		try{
			obj.getBooksByGenre("fiction", new JacksonFactory(), null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
