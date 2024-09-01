package com.xboot.jpa.demo.dal.h2;

import com.xboot.jpa.demo.dal.dataobject.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 注释
 *
 * @author xboot
 **/
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("from Book order by id limit 1000")
    List<Book> findContentMaxLength();
}
