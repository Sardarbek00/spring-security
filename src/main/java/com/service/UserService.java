package com.service;

import com.model.User;
import com.model.enums.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user){
        if (user.getEmail()==null){
            throw new RuntimeException("Email is null");
        }
        if (user.getFirstName().length()<2){
            throw new RuntimeException("Имя должно содержит больше 2 буквы");
        }
        if (findAll().isEmpty()){
            user.setRole(Role.ADMIN);
        }else {
            user.setRole(Role.STUDENT);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
    }

    public User findById(Long id){
        return entityManager.find(User.class,id);
    }

    public void update(Long id,User user){
        User oldUser=findById(id);
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        oldUser.setRole(user.getRole());
       entityManager.merge(user);
    }
    public List<User> findAll(){
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    public void delete(Long id){
        User user=findById(id);
        entityManager.remove(user);
    }

    public User findByEmail(String email){
        return entityManager.createQuery("select user from User user where user.email=: email", User.class)
                .setParameter("email",email).getSingleResult();
    }
}
