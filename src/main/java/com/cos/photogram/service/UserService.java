package com.cos.photogram.service;

import com.cos.photogram.domain.subscribe.SubScribeRepository;
import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import com.cos.photogram.handler.ex.CustomApiException;
import com.cos.photogram.handler.ex.CustomException;
import com.cos.photogram.handler.ex.CustomValidationApiException;
import com.cos.photogram.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final SubScribeRepository subScribeRepository;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public User 회원프로필사진변경(int principalId, MultipartFile profileImageFile){
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" +profileImageFile.getOriginalFilename(); // 1.jpg
        System.out.println("이미지 파일이름 : "+ imageFileName);

        Path imageFilePath = Paths.get(uploadFolder + imageFileName);


        // 통신, I/O -> 예외 발생 가능
        try {
            Files.write(imageFilePath, profileImageFile.getBytes());
        } catch(Exception e){
            e.printStackTrace();
        }

        User userEntity = userRepository.findById(principalId).orElseThrow(() ->{
            throw new CustomApiException("유저를 찾을 수 없습니다.");
        });
        userEntity.setProfileImageUrl(imageFileName);

        return userEntity;
    }

    @Transactional(readOnly = true)
    public UserProfileDto 회원프로필(int pageUserId, int principalId) { //userId를 받아서 UserController mapping에 넘김
        UserProfileDto dto = new UserProfileDto();
        // SELECT * FROM image WHERE userId = :userId;

        User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });

        dto.setUser(userEntity);
        dto.setPageOwnerState(pageUserId == principalId); // 1은 페이지 주인, -1은 주인 아님
        dto.setImageCount(userEntity.getImages().size());

        int subscribeState = subScribeRepository.mSubscribeState(principalId, pageUserId);
        int subscribeCount = subScribeRepository.mSubscribeCount(pageUserId);

        dto.setSubscribeCount(subscribeCount);
        dto.setSubscribeState(subscribeState == 1);

        //likeCount 추가
        userEntity.getImages().forEach((image) -> {
            image.setLikeCount(image.getLikes().size());
        });

        return dto;
    }


    @Transactional
    public User 회원수정(int id, User user){
        //1. 영속화
        User userEntity = userRepository.findById(id).orElseThrow(()-> {
            return new CustomValidationApiException("찾을 수 없는 id 입니다.");
        });

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        //2. 영속화된 오브젝트를 수정 - 더티체킹
        userEntity.setName(user.getName());
        userEntity.setPassword(encPassword);
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());
        return userEntity;
    } // 더티체킹 일어나서 업데이트 완료됨.
}
