package com.atguigu.gmall.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author dalaoban
 * @create 2020-02-13-13:51
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("secret"));
    }
}
