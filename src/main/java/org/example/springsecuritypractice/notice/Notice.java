package org.example.springsecuritypractice.notice;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@NoArgsConstructor()
public class Notice {

    @Id
    @GeneratedValue
    private Long id;

    //공지사항 내용
    private String title;

    @Lob
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Notice(
            String title,
            String content
    ){
        this.title = title;
        this.content = content;
    }


}
