package com.luan.learnspring.controller;

import com.luan.learnspring.dto.BookSearchDto;
import com.luan.learnspring.model.Book;
import com.luan.learnspring.repository.BookRepository;
import com.luan.learnspring.specification.GenSpec;
import com.luan.learnspring.specification.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
public class BookController {

    private final BookRepository bookRepository;

    @GetMapping
    public Page<Book> getBooks(Pageable pageable, BookSearchDto search) {
        Specification<Book> spec = new SpecificationBuilder<Book>()
                .with(GenSpec.containsIgnoreCase("title", search.title()))
                .with(GenSpec.containsIgnoreCase("author.name", search.author()))
                .with(GenSpec.equals("year", search.year()))
                .with(GenSpec.equals("publishedAt", search.publishedAt()))
                .build();

        return bookRepository.findAll(spec, pageable);
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> test(@RequestParam Map<String, Object> params) {
        return ResponseEntity.ok(params);
    }

}
