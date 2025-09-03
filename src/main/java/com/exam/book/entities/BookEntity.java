package com.exam.book.entities;

import com.exam.book.constant.ColumnName;
import com.exam.book.constant.TableName;
import com.exam.book.enums.BookStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = TableName.TB_BOOK)
@Data
public class BookEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = ColumnName.BOOK_ID)
    private Integer bookId;

    @Column(name = ColumnName.BOOK_NAME)
    private String bookName;

    @Column(name=ColumnName.ISBN)
    private String isbn;

    @Column(name = ColumnName.AUTHOR)
    private String author;

    @Column(name=ColumnName.BOOK_STATUS)
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;


}
