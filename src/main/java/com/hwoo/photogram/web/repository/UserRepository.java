package com.hwoo.photogram.web.repository;

import com.hwoo.photogram.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 어노테이션이 없어도 JpaRepository를 상속하면 IoC 등록이 자동으로 된다.
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}