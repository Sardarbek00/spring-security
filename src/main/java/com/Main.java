package com;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main {
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

        String password="Sardar";
        System.out.println(bCryptPasswordEncoder.encode(password));
    }
}
