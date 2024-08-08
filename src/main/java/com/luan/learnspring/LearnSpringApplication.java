package com.luan.learnspring;

import com.luan.learnspring.model.Author;
import com.luan.learnspring.model.Book;
import com.luan.learnspring.repository.AuthorRepository;
import com.luan.learnspring.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class LearnSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnSpringApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(AuthorRepository authorRepository, BookRepository bookRepository) {
        return (args) -> {
            Author author1 = Author.builder().name("Luan").build();
            Author author2 = Author.builder().name("João Silva").build();

            authorRepository.saveAll(Arrays.asList(author1, author2));

            Book book1 = Book.builder().title("Philosophers Stone").year(2020).publishedAt(LocalDate.now()).author(author1).build();
            Book book2 = Book.builder().title("Chamber of Secrets").year(2020).publishedAt(LocalDate.now()).author(author1).build();
            Book book3 = Book.builder().title("A Game of Thrones").year(2021).publishedAt(LocalDate.now()).author(author2).build();
            Book book4 = Book.builder().title("A Clash of Kings").year(2024).publishedAt(LocalDate.now()).author(author2).build();

            bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4));
        };
    }

}
