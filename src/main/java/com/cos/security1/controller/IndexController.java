package com.cos.security1.controller;

import com.cos.security1.config.auth.PrincipalDetail;
import com.cos.security1.entity.User;
import com.cos.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.Response;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping({"", "/"})
    public String index() {
        return "index";
    }

    @GetMapping("user")
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    @GetMapping("loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("join")
    public String join(User user) {
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/loginForm";
    }

    // config 에 작성하지 않고 특정 권한에 따라 제한하고 싶을 때 사용 -> config 에서 @EnableGlobalMethodSecurity 에서 활성화해야 사용 가능
    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    // Secured 랑 비슷하게 사용하지만 여러개 권한에 대해 처리하는 경우 사용 -> 마찬가지로 @EnableGlobalMethodSecurity 에서 활성화해서 사용
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    // @PostAuthorize 어노테이션은 해당 메서드가 실행된 후 처리
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "데이터정보";
    }

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetail userDetails) {
        System.out.println("/test/login ===========================");
        PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();
        System.out.println("authentication: " + principalDetail.getUser());

        System.out.println("userDetails: " + userDetails.getUser());
        return "세션 정보 확인하기";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOauthLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth2) {
        System.out.println("/test/oauth/login ===========================");
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("oauth2user: " + oauth2User.getAttributes());
        System.out.println("oauth2: " + oauth2.getAttributes());
        return "세션 정보 확인하기";
    }

}
