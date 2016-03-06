package org.bhanu.book.client.search;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.bhanu.book.client.Credentials;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;

public class SampleCalls {
	
	public void getBooks(JacksonFactory jsFactory) throws GeneralSecurityException, IOException{
		final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsFactory, null)
        .setApplicationName(Credentials.APP_NAME)
        .setGoogleClientRequestInitializer(new BooksRequestInitializer(Credentials.API_KEY))
        .build();
		System.out.println(books);
		
		
		com.google.api.services.books.Books.Volumes.List volumesList = books.volumes().list("Johnsubject:fiction");
		//volumesList.set("subject", "fiction");
		Volumes volumes = volumesList.execute();
		
		for (Volume volume : volumes.getItems()) {
		      Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
		      Volume.SaleInfo saleInfo = volume.getSaleInfo();
		      System.out.println("==========");
		      // Title.
		      System.out.println("Title: " + volumeInfo.getTitle());
		      // Author(s).
		      java.util.List<String> authors = volumeInfo.getAuthors();
		      if (authors != null && !authors.isEmpty()) {
		        System.out.print("Author(s): ");
		        for (int i = 0; i < authors.size(); ++i) {
		          System.out.print(authors.get(i));
		          if (i < authors.size() - 1) {
		            System.out.print(", ");
		          }
		        }
		        System.out.println();
		      }
		}
		
	}
	public static void main(String[] args) {
		SampleCalls obj = new SampleCalls();
		try{
		obj.getBooks(new JacksonFactory());
		}catch(Exception e){
			System.out.println("exception caught:" + e);
		}
	}
}	
