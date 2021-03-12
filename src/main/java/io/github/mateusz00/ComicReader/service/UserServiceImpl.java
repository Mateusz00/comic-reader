package io.github.mateusz00.ComicReader.service;

import io.github.mateusz00.ComicReader.dao.UserRepository;
import io.github.mateusz00.ComicReader.dto.UserRegistration;
import io.github.mateusz00.ComicReader.entity.User;
import io.github.mateusz00.ComicReader.exception.EmailExistsException;
import io.github.mateusz00.ComicReader.exception.UsernameExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User registerUser(UserRegistration userDto) {
        if(userRepository.findByUsername(userDto.getUsername()).isPresent())
            throw new UsernameExistsException(userDto.getUsername());

        if(userRepository.findByEmail(userDto.getEmail()).isPresent())
            throw new EmailExistsException(userDto.getEmail());

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRole("ROLE_ADMIN");
        user.setActive(true); // TODO: Add activation through email
        userRepository.saveAndFlush(user);

        return user;
    }
}
