package io.github.mateusz00.ComicReader.service;

import io.github.mateusz00.ComicReader.dao.UserRepository;
import io.github.mateusz00.ComicReader.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username: "
                + username + " not found!"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getGrantedAuthorities());
    }
}
