package com.cos.photogram.domain.image;

import com.cos.photogram.domain.comment.Comment;
import com.cos.photogram.domain.likes.Likes;
import com.cos.photogram.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = {"user", "likes", "comment"})
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String caption;
    private String postImageUrl; // 사진을 전송받아서 그 사진을 서버에 특적 폴더에 저장 - DB에 저장된 경로를 insert


    @JsonIgnoreProperties({"images"})
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;

    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image")
    private List<Likes> likes;

    private LocalDateTime createDate;

    @OrderBy("id DESC")
    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image")
    private List<Comment> comments;

    @Transient // DB에 컬럼이 만들어지지 않는다.
    private boolean likeState;

    @Transient
    private int likeCount;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }


}
