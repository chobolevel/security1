package com.cos.security1.config.auth;

import com.cos.security1.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 시큐리티는 로그인을 하면 session을 생성(key = Security ContextHolder)
// 가능한 value 오브젝트  -> Authentication 타입 객체
// Authentication 내부에 User정보를 넣을 수 있는데 가능한 타입은 UserDetails타입
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrincipalDetail implements UserDetails {

    private User user;

    // 해당 User의 권한 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add((GrantedAuthority) () -> user.getRole());
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
