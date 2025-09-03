package com.exam.book.entities;

import com.exam.book.constant.ColumnName;
import com.exam.book.constant.TableName;
import com.exam.book.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = TableName.TB_MEMBER)
@Data
public class MemberEntity extends BaseEntity {
    @Id
    @Column(name = ColumnName.MEMBER_ID)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer memberId;

    @Column(name=ColumnName.MEMBER_NAME)
    private String memberName;

    @Column(name=ColumnName.MEMBER_LASTNAME)
    private String memberLastname;

    @Column(name=ColumnName.EMAIL)
    private String email;

    @Column(name=ColumnName.PASSWORD)
    private String password;

    @Column(name=ColumnName.ROLE)
    @Enumerated(EnumType.STRING)
    private Role role;


}
