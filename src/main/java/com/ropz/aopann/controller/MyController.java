package com.ropz.aopann.controller;

import com.ropz.aopann.token.AuthToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author ropz
 */
@RestController
public class MyController {

    @GetMapping("hello")
    public String hello() {
        System.out.println("hi,我不需要权限");
        return "hi,我不需要权限";
    }

    @AuthToken
    @GetMapping("user")
    public String user(String id, HttpSession session) {
        System.out.println("hi,我需要登入" + id);
        session.setAttribute("id", id);
        return "hi,我需要登入";
    }

    @AuthToken(roleName = "admin")
    @GetMapping("admin")
    public String admin(String role) {
        System.out.println("hi,我需要管理员权限");
        return "hi,我需要管理员权限";
    }
}
