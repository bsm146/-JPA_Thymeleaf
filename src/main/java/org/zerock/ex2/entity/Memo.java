package org.zerock.ex2.entity;

import lombok.*;
import org.springframework.boot.autoconfigure.quartz.QuartzTransactionManager;

import javax.persistence.*;

@Entity(name = "abc")
@Table(name = "tbl_memo")
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;


}
