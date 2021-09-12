package com.cos.photogram.service;

import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import com.cos.photogram.handler.ex.CustomException;
import com.cos.photogram.handler.ex.CustomValidationApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public User 회원프로필(int userId) { //userId를 받아서 UserController mapping에 넘김
        // SELECT * FROM image WHERE userId = :userId;

        User userEntity = userRepository.findById(userId).orElseThrow(() -> {
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });

        return userEntity;
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
