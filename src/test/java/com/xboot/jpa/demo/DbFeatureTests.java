package com.xboot.jpa.demo;

import com.xboot.jpa.demo.dal.dataobject.Book;
import com.xboot.jpa.demo.dal.h2.BookRepository;
import org.dromara.hutool.core.io.file.FileUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * 注释
 *
 * @author xboot
 **/
@SpringBootTest(classes = JpaDemoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DbFeatureTests {

    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    public void beforeEach() {
        bookRepository.deleteAll();
    }

    @Test
    public void testSave() {
        Book book = new Book();
        book.setName("Spring Boot JPA");
        book.setAuthor("xboot");
        book.setPrice(10.0);
        book.setContent("Spring Boot JPA is a good book.");
        bookRepository.save(book);

        Iterable<Book> repositoryAll = bookRepository.findAll();
        Iterator<Book> iterator = repositoryAll.iterator();
        if (iterator.hasNext()) {
            iterator.next();
            Assertions.assertFalse(iterator.hasNext());
        }
    }

    @Test
    public void testSaveOccurredErrorsAndRollback() {
        Book book = new Book();
        book.setName("Spring Boot JPA");
        book.setAuthor("xboot");
        book.setPrice(10.0);
        File file = FileUtil.file("big_text.txt");
        book.setContent(FileUtil.readString(file, Charset.defaultCharset()));
        try {
            bookRepository.save(book);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        Iterable<Book> repositoryAll = bookRepository.findAll();
        boolean exist = repositoryAll.iterator().hasNext();
        Assertions.assertFalse(exist);
    }

    @Test
    public void testFindById() {
        Book book = new Book();
        book.setName("Spring Boot JPA");
        book.setAuthor("xboot");
        book.setPrice(10.0);
        book.setContent("Spring Boot JPA is a good book.");
        bookRepository.save(book);

        Optional<Book> optionalBook = bookRepository.findById(book.getId());
        if (optionalBook.isPresent()) {
            Book book1 = optionalBook.get();
            Assertions.assertEquals(book.getId(), book1.getId());
        }
    }

    @Test
    public void testFindPaged() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            Book book = new Book();
            book.setName("Spring Boot JPA" + i);
            book.setAuthor("xboot");
            book.setPrice(10.0 + i);
            book.setContent("Spring Boot JPA is a good book.");
            books.add(book);
        }
        List<Book> savedAll = bookRepository.saveAll(books);
        Page<Book> bookPage = bookRepository.findAll(PageRequest.of(1, 10));
        Assertions.assertEquals(2, bookPage.getTotalPages());
        Assertions.assertEquals(11, bookPage.getTotalElements());
    }

    @Test
    public void testFindAll() {
        // todo ignored
    }

    @Test
    public void testUpdate() {
        Book book = new Book();
        book.setName("Spring Boot JPA");
        book.setAuthor("xboot");
        book.setPrice(10.0);
        book.setContent("Spring Boot JPA is a good book.");
        bookRepository.save(book);

        book.setName("Spring Boot JPA 2");
        Book updated = bookRepository.save(book);
        Assertions.assertEquals("Spring Boot JPA 2", updated.getName());

        // update price only
        Book updatePriceBook = bookRepository.findById(book.getId()).get();
        updatePriceBook.setId(book.getId());
        updatePriceBook.setPrice(12.99);
        Book updatePrice = bookRepository.save(updatePriceBook);
        Assertions.assertEquals(12.99, updatePrice.getPrice());
        Assertions.assertEquals("Spring Boot JPA 2", updatePrice.getName());

        book.setName(null);
        Book updated2 = bookRepository.save(book);
        Assertions.assertNull(updated2.getName());
    }

    @Test
    public void testUpdateOccurredErrorsAndRollback() {

    }

    @Test
    public void testDeleteById() {
        Book book = new Book();
        book.setName("Spring Boot JPA");
        book.setAuthor("xboot");
        book.setPrice(10.0);
        book.setContent("Spring Boot JPA is a good book.");
        bookRepository.save(book);

        bookRepository.deleteById(book.getId());
    }

    @Test
    public void testDeleteAll() {
        Book book = new Book();
        book.setName("Spring Boot JPA");
        book.setAuthor("xboot");
        book.setPrice(10.0);
        book.setContent("Spring Boot JPA is a good book.");
        bookRepository.save(book);

        bookRepository.deleteAll();
    }


}
