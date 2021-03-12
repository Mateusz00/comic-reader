package io.github.mateusz00.ComicReader.controller;

import io.github.mateusz00.ComicReader.dto.UserRegistration;
import io.github.mateusz00.ComicReader.entity.User;
import io.github.mateusz00.ComicReader.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistration userDto) {
        User user = userService.registerUser(userDto);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
