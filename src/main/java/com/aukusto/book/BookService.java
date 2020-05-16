package com.aukusto.book;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class BookService {

    List<BookEntity> all() {
        return BookEntity.listAll();
    }

    @Transactional
    BookEntity add(BookRequest request) {
        var bookEntity = new BookEntity();
        bookEntity.title = request.getTitle();
        bookEntity.author = request.getAuthor();
        bookEntity.publicatedOn = request.getPublicatedOn();
        bookEntity.persist();
        return bookEntity;
    }
}
