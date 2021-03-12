package io.github.mateusz00.ComicReader.service;

import io.github.mateusz00.ComicReader.dto.UserRegistration;
import io.github.mateusz00.ComicReader.entity.User;

public interface UserService
{
    User registerUser(UserRegistration user);
}
