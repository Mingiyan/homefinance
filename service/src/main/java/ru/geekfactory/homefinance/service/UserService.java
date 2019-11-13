package ru.geekfactory.homefinance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekfactory.homefinance.dao.model.User;
import ru.geekfactory.homefinance.dao.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements ServiceCRUD<Long, User> {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void save(User object) {
        userRepository.save(object);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User update(User object) {
        return userRepository.save(object);
    }

    @Override
    public void remove(User object) {
        userRepository.delete(object);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
