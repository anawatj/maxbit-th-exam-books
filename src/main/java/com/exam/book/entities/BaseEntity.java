package com.exam.book.entities;

import com.exam.book.constant.ColumnName;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class BaseEntity {


    @Column(name = ColumnName.CREATED_BY)
    private String createdBy;

    @Column(name=ColumnName.CREATED_DATE)
    private LocalDateTime createdDate;

    @Column(name=ColumnName.UPDATED_BY)
    private String updatedBy;

    @Column(name=ColumnName.UPDATED_DATE)
    private LocalDateTime updatedDate;

}
