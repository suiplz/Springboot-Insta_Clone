package com.cos.photogram.web;

import com.cos.photogram.config.auth.PrincipalDetails;
import com.cos.photogram.domain.user.User;
import com.cos.photogram.service.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{id}")
    public String profile(@PathVariable int id, Model model) {
        User userEntity = userService.회원프로필(id);
        model.addAttribute("user",userEntity);
        return "user/profile";
    }

    @GetMapping({ "/user/{id}/update"})
    public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        //System.out.println("세션 정보:"+principalDetails.getUser());


        return "user/update";
    }
}
