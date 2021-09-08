package com.cos.photogram.domain.user;

import com.cos.photogram.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

//annotation 없어도 JpaRepository 상속하면 Ioc 자동으로 등록
public interface UserRepository extends JpaRepository<User,Integer> {
    //JPA query method
    User findByUsername(String username);

}
