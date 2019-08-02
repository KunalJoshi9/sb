package com.example.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Book;
import com.example.repo.BookRepository;

@RestController
@RequestMapping("books")
public class BookController {

	@Autowired
	BookRepository repo;

	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<?> getBook(@PathVariable int id) {
		Book book = findBookById(id).orElseThrow();
		return ResponseEntity.ok(book);
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<?> getAllBooks() {
		Iterable<Book> books = repo.findAll();
		return ResponseEntity.ok(books);
	}

	@PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody Book book) {
		Book fetchedBook = repo.save(book);
		return ResponseEntity.ok(fetchedBook);
	}

	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> update(@RequestBody Book book, @PathVariable int id) {
		if (book.getId() != id) {
			return ResponseEntity.notFound().build();
		}
		if (findBookById(id).isPresent()) {
			Book updatedBook = repo.save(book);
			return ResponseEntity.ok(updatedBook);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	
	  //TODO
	  
	/*
	 * @PatchMapping(path="/{id}")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public ResponseEntity<?>
	 * partialUpdateTitle(@RequestBody String title, @PathVariable int id){
	 * if(findBookById(id).isPresent()) { Book updatedBook = repo.up return
	 * ResponseEntity.ok(updatedBook); }else { return
	 * ResponseEntity.status(HttpStatus.NOT_FOUND).build(); } }
	 */
	 
	@DeleteMapping(path="/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> deleteBook(@PathVariable int id) {
		Optional<Book> optionalBook = findBookById(id);
		if (optionalBook.isPresent()) {
			repo.delete(optionalBook.get());
			return ResponseEntity.ok("Deleted Book");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	private Optional<Book> findBookById(int id) {
		return repo.findById(Long.valueOf(Integer.toString(id)));
	}
}
