package com.cos.security1.repository;

import com.cos.security1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD 함수를 Jpa Repository가 들고있음
// @Repository라은 어노테이션 없어도 빈으로 등록 JpaRepository 상속하기 때문
public interface UserRepository extends JpaRepository<User, Integer> {

    // JPA query method
    // 작성 규칙 -> findByXXX로 작성하면 XXX를 WHERE 절에서 검색하는 코드를 사용
    // SELECT * FROM user WHERE username=?;
    public User findByUsername(String username);

}
