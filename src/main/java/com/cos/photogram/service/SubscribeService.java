package com.cos.photogram.service;

import com.cos.photogram.domain.subscribe.SubScribeRepository;
import com.cos.photogram.domain.subscribe.Subscribe;
import com.cos.photogram.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubScribeRepository subScribeRepository;

    @Transactional
    public void 구독하기(int fromUserId, int toUserId) {
        try {
            subScribeRepository.mSubscribe(fromUserId, toUserId);
        } catch (Exception e){
            throw new CustomApiException("이미 구독을 했습니다.");
        }
    }

    @Transactional
    public void 구독취소하기(int fromUserId, int toUserId){
        subScribeRepository.mUnSubscribe(fromUserId, toUserId);
    }
}
