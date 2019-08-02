package com.example.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.domain.Book;

public interface BookRepository extends PagingAndSortingRepository<Book, Long>{

}
