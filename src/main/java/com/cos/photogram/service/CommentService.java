package com.cos.photogram.service;

import com.cos.photogram.domain.comment.Comment;
import com.cos.photogram.domain.comment.CommentRepository;
import com.cos.photogram.domain.image.Image;
import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import com.cos.photogram.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    @Transactional
    public Comment 댓글쓰기(String content, int imageId, int userId) {

        // 객체를 만들 때 id값만 담아서 insert 할 수 있다.
        // return 시 image 객체와 user객체는 id값만 갖고있는 빈 객체 리턴받는다.
        Image image = new Image();
        image.setId(imageId);
        User user = userRepository.findById(userId).orElseThrow(() ->{
            throw new CustomApiException("유저 아이디를 찾을 수 없습니다.");
        });
        user.setId(userId);

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setImage(image);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    @Transactional
    public void 댓글삭제(int id) {
        try{
            commentRepository.deleteById(id);
        } catch (Exception e){
            throw new CustomApiException(e.getMessage());
        }

    }
}
