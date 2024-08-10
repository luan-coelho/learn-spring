package com.luan.learnspring.specification;

import com.luan.learnspring.model.Author;
import com.luan.learnspring.model.Book;
import com.luan.learnspring.model.Phone;
import com.luan.learnspring.repository.AuthorRepository;
import com.luan.learnspring.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@RequiredArgsConstructor
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

    @DisplayName("""
            Quando parametros estiverem corretos e utilizar operacao Equals entao deve encontrar registro com sucesso
            """)
    @Test
    void fromParams_whenParameterIsCorrectAndOperationIsEquals_findRecordSuccessfully() {
        Map<String, String> params = new HashMap<>();
        String equalsOperation = SpecificationBuilder.Operation.EQUALS.getValue();
        params.put("title", equalsOperation + ",Philosophers Stône");
        Specification<Book> spec = new SpecificationBuilder<Book>()
                .fromParams(params)
                .build();

        List<Book> books = bookRepository.findAll(spec);

        assertEquals(1, books.size());
    }

    @DisplayName("""
            Quando parametro estiver incorreto e utilizar operacao Equals entao nao deve encontrar nenhum registro
            """)
    @Test
    void fromParams_whenParameterIsIncorrectAndOperationIsEquals_thenShouldNotFindAnyRecord() {
        Map<String, String> params = new HashMap<>();
        String equalsOperation = SpecificationBuilder.Operation.EQUALS.getValue();
        params.put("title", equalsOperation + ",Philosophers");
        Specification<Book> spec = new SpecificationBuilder<Book>()
                .fromParams(params)
                .build();

        List<Book> books = bookRepository.findAll(spec);

        assertEquals(0, books.size());
    }

    @DisplayName("""
            Quando parametro conter acento e utilizar operacao Contains entao deve encontrar registro sem acento com
            sucesso
            """)
    @Test
    void fromParams_whenParameterHasAccentAndOperationIsEquals_thenShouldFindRecordWithoutAccentSuccessfully() {
        Map<String, String> params = new HashMap<>();
        String containsOperation = SpecificationBuilder.Operation.CONTAINS.getValue();
        params.put("title", containsOperation + ",Chambér of Sêcrets");
        Specification<Book> spec = new SpecificationBuilder<Book>()
                .fromParams(params)
                .build();

        List<Book> books = bookRepository.findAll(spec);

        assertEquals(1, books.size());
    }

    @DisplayName("""
            Quando parametro nao conter acento e utilizar operacao Contains entao deve encontrar registro com acento com
            sucesso
            """)
    @Test
    void fromParams_whenParameterHasNoAccentAndOperationIsContains_thenShouldFindRecordWithAccentSuccessfully() {
        Map<String, String> params = new HashMap<>();
        String containsOperation = SpecificationBuilder.Operation.CONTAINS.getValue();
        params.put("title", containsOperation + ",Philosophers Stone");
        Specification<Book> spec = new SpecificationBuilder<Book>()
                .fromParams(params)
                .build();

        List<Book> books = bookRepository.findAll(spec);

        assertEquals(1, books.size());
    }

    @Test
    void fromParams_quandoParametrosEstiveremCorretosEUtilizarVariasOperacoes_encontrarRegistroComSucesso() {
        Map<String, String> params = new HashMap<>();
        String equalsOperation = SpecificationBuilder.Operation.EQUALS.getValue();
        String likeCaseOperation = SpecificationBuilder.Operation.LIKE.getValue();
        String containsOperation = SpecificationBuilder.Operation.CONTAINS.getValue();
        String containsIgnoreCaseOperation = SpecificationBuilder.Operation.CONTAINS_IGNORE_CASE.getValue();
        params.put("title", equalsOperation + ",Philosophers Stône");
        params.put("author.name", likeCaseOperation + ",Luan");
        params.put("author.phone.number", containsOperation + ",3456");
        params.put("author.phone.type", containsIgnoreCaseOperation + ",Mobile");
        Specification<Book> spec = new SpecificationBuilder<Book>()
                .fromParams(params)
                .build();

        List<Book> books = bookRepository.findAll(spec);

        assertEquals(1, books.size());
    }

    protected void init() {
        authorRepository.deleteAll();
        bookRepository.deleteAll();

        Phone phone1 = Phone.builder().number("123456789").type("mobile").build();
        Phone phone2 = Phone.builder().number("987654321").type("home").build();

        Author author1 = Author.builder().name("Luan").phone(phone1).build();
        Author author2 = Author.builder().name("João Silva").phone(phone2).build();

        authorRepository.saveAll(Arrays.asList(author1, author2));

        LocalDate now = LocalDate.now();
        Book book1 = Book.builder().title("Philosophers Stône").year(2020).publishedAt(now).author(author1).build();
        Book book2 = Book.builder().title("Chamber of Secrets").year(2020).publishedAt(now).author(author1).build();
        Book book3 = Book.builder().title("A Game of Thrones").year(2021).publishedAt(now).author(author2).build();
        Book book4 = Book.builder().title("A Clash of Kings").year(2024).publishedAt(now).author(author2).build();

        bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4));
    }

}