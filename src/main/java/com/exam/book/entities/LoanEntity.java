package com.exam.book.entities;

import com.exam.book.constant.ColumnName;
import com.exam.book.constant.TableName;
import com.exam.book.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = TableName.TB_LOAN)
public class LoanEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = ColumnName.LOAN_ID)
    private Integer loanId;

    @Column(name=ColumnName.LOAN_DATE)
    private LocalDate loanDate;

    @Column(name = ColumnName.RETURN_DATE)
    private LocalDate returnDate;

    @Column(name=ColumnName.LOAN_STATUS)
    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ColumnName.LOAN_MEMBER,referencedColumnName = ColumnName.MEMBER_ID)
    private MemberEntity member;

    @ManyToMany(fetch = FetchType.LAZY,cascade =CascadeType.ALL)
    @JoinTable(
            name = TableName.TB_LOAN_DETAIL,
            joinColumns = @JoinColumn(name = ColumnName.LOAN_ID,referencedColumnName = ColumnName.LOAN_ID),
            inverseJoinColumns = @JoinColumn(name = ColumnName.BOOK_ID,referencedColumnName = ColumnName.BOOK_ID))
    private List<BookEntity> books;

}
