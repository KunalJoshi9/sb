package com.example.exception;


public class BookNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String message;

	public BookNotFoundException(int id) {
		this.message = "Book with id "+id+" does not exist";
	}

	
}
