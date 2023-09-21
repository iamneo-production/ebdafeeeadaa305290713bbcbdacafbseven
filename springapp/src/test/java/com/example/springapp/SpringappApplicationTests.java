package com.example.springapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.springapp.controller.BookController;
import com.example.springapp.model.Book;
import com.example.springapp.service.BookService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.mockito.Mockito;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class SpringappApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService; // This will be a mock instance of BookService

    @BeforeEach
    public void setUp() {
        // Mock behavior for the bookService in the controller
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(new Book(1, "Book 1", "Author 1", 2020));
        mockBooks.add(new Book(2, "Book 2", "Author 1", 2019));
        
        // Use doReturn instead of when for setting up the mock behavior
        Mockito.doReturn(mockBooks).when(bookService).getBooksByAuthor("Author 1");
    }

    @Test
    public void testGetAllBooks() throws Exception {
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(new Book(1, "Book 1", "Author 1", 2020));
        mockBooks.add(new Book(2, "Book 2", "Author 2", 2019));
        mockBooks.add(new Book(3, "Book 3", "Author 3", 2021));

        // Mock behavior
        Mockito.when(bookService.getAllBooks()).thenReturn(mockBooks);
    }

    @Test
    public void testGetBookById() throws Exception {
        // Mock data
        Book mockBook = new Book(1, "Book 1", "Author 1", 2020);

        // Mock behavior
        Mockito.when(bookService.getBookById(1)).thenReturn(mockBook);

        // Test
        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Book 1"))
                .andExpect(jsonPath("$.author").value("Author 1"))
                .andExpect(jsonPath("$.year").value(2020));
    }



	@Test
	public void testGetBooksByNonExistentAuthor() throws Exception {
		// Mock behavior for an author with no books
		Mockito.doReturn(new ArrayList<>()).when(bookService).getBooksByAuthor("NonExistentAuthor");
	
		// Test
		mockMvc.perform(get("/books/authors/NonExistentAuthor"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	
	

    @Test
    public void testGetBooksByYear() throws Exception {
        // Mock data
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(new Book(1, "Book 1", "Author 1", 2020));
        mockBooks.add(new Book(2, "Book 2", "Author 2", 2020));

        // Mock behavior
        Mockito.when(bookService.getBooksByYear(2020)).thenReturn(mockBooks.subList(0, 2));

        // Test
        mockMvc.perform(get("/books/year/2020"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[1].title").value("Book 2"));
    }

	@Test
public void testGetNonExistentBookById() throws Exception {
    // Mock behavior for a non-existent book ID
    Mockito.doReturn(null).when(bookService).getBookById(100);

    // Test
    mockMvc.perform(get("/books/100"))
            .andExpect(status().isOk())
            .andExpect(content().string(""));
}
@Test 
    public void testcontrollerfolder() { 
        String directoryPath = "src/main/java/com/example/springapp/controller"; // Replace with the path to your directory 
        File directory = new File(directoryPath); 
        assertTrue(directory.exists() && directory.isDirectory()); 
    }
    
    @Test 
    public void testcontrollerfile() { 
        String filePath = "src/main/java/com/example/springapp/controller/BookController.java"; 
        // Replace with the path to your file 
        File file = new File(filePath); 
        assertTrue(file.exists() && file.isFile()); 
    }

	@Test 
    public void testModelFolder() { 
        String directoryPath = "src/main/java/com/example/springapp/model"; // Replace with the path to your directory 
        File directory = new File(directoryPath); 
        assertTrue(directory.exists() && directory.isDirectory()); 
    }
    
    @Test 
    public void testModelFile() { 
        String filePath = "src/main/java/com/example/springapp/model/Book.java"; 
        // Replace with the path to your file 
        File file = new File(filePath); 
        assertTrue(file.exists() && file.isFile()); 
    }

	
	@Test 
    public void testServiceFolder() { 
        String directoryPath = "src/main/java/com/example/springapp/service"; // Replace with the path to your directory 
        File directory = new File(directoryPath); 
        assertTrue(directory.exists() && directory.isDirectory()); 
    }
    
    @Test 
    public void testServieFile() { 
        String filePath = "src/main/java/com/example/springapp/service/BookService.java"; 
        // Replace with the path to your file 
        File file = new File(filePath); 
        assertTrue(file.exists() && file.isFile()); 
    }

}


