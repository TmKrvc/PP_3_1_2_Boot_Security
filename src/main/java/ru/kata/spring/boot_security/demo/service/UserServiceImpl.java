package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }


    @Override
    public User findEmail(String email) {
        return userDao.findEmail(email);
    }

    @Transactional
    @Override
    public void update(User user, int id, String[] roles, String pass) {
        user.setId(id);
        user.setPassword(passwordEncoder.encode(pass));
        user.setRoles(Arrays.stream(roles)
                .map(role -> roleService.findRoles(role)) //
                .collect(Collectors.toList()));
        userDao.update(user);
    }

    @Transactional
    @Override
    public void delete(int id) {
        userDao.delete(id);
    }
    @Transactional
    @Override
    public void save(User user, String[] roles, String pass) {
        user.setPassword(passwordEncoder.encode(pass));
        user.setRoles(Arrays.stream(roles)
                .map(role -> roleService.findRoles(role))//
                .collect(Collectors.toList()));
        userDao.save(user);
    }

    @Override
    public User findUser(int id) {
        return userDao.findUser(id);
    }

}