package com.luan.learnspring.specification;

import com.luan.learnspring.model.Author;
import com.luan.learnspring.model.Book;
import com.luan.learnspring.repository.AuthorRepository;
import com.luan.learnspring.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SpecificationBuilderTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        init();
    }

    @Test
    void fromParams() {
        Map<String, String> params = new HashMap<>();
        params.put("title", SpecificationBuilder.Operation.CONTAINS.getValue() + ",Philosophers Stone");
        Specification<Book> spec = new SpecificationBuilder<Book>()
                .fromParams(params)
                .build();

        List<Book> books = bookRepository.findAll(spec);
    }

    private void init() {
        Author author1 = Author.builder().name("Luan").build();
        Author author2 = Author.builder().name("João Silva").build();

        authorRepository.saveAll(Arrays.asList(author1, author2));

        Book book1 = Book.builder().title("Philosophers Stone").year(2020).publishedAt(LocalDate.now()).author(author1).build();
        Book book2 = Book.builder().title("Chamber of Secrets").year(2020).publishedAt(LocalDate.now()).author(author1).build();
        Book book3 = Book.builder().title("A Game of Thrones").year(2021).publishedAt(LocalDate.now()).author(author2).build();
        Book book4 = Book.builder().title("A Clash of Kings").year(2024).publishedAt(LocalDate.now()).author(author2).build();

        bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4));
    }

}