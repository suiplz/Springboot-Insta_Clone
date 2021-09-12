package com.cos.photogram.domain.user;

import com.cos.photogram.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // DB에 Table 생성
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라감
    private int id;

    @Column(length = 20, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String website;
    private String bio;
    @Column(nullable = false)
    private String email;
    private String gender;
    private String profileImageUrl;
    private String role;

    // 나는 연관관계의 주인이 아니다. 그러므로 테이블에 컬럼을 만들지 마
    // User를 SELECT할 때 해당 User id로 등록된 image 전부 가져와
    // LAZY = User SELECT시 해당 User id로 등록된 image들을 가져오지마 - getimages() 함수의 iamge들이 호출될 때 가져오기
    // Eager = User SELECT시 해당 User id로 등록된 image들을 전부 Join해서 가져오기
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"})
    private List<Image> images; // 양방향매핑

    private String phone;

    private LocalDateTime createDate;

    @PrePersist // DB에 INSERT 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
