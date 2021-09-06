package com.cos.photogram.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

//annotation 없어도 JpaRepository상속하면 Ioc 자동으로 등록
public interface UserRepository extends JpaRepository<User,Integer> {

}
