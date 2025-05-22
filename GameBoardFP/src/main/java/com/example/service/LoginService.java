package com.example.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {
	
	 private final UserRepository userRepository;

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        var user = this.userRepository.findByName(username);
	        // もしユーザが見つからなかった場合、例外を投げる
	        if (user == null) {
	            throw new UsernameNotFoundException("User:" + username + " not found");
	        }
	        return createUser(user);
	    }
	 

	    private UserDetails createUser(com.example.entity.User user) {

	        Set<GrantedAuthority> auth = new HashSet<>();
	        // ロール登録
	        auth.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
	        User userDetails = new User(user.getName(), user.getPass(), auth);
	        return userDetails;
	    }
}
