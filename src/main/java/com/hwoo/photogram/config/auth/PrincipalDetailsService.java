package com.hwoo.photogram.config.auth;

import com.hwoo.photogram.domain.user.User;
import com.hwoo.photogram.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 1. 패스워드는 알아서 체킹
    // 2. 리턴이 잘 되면 자동으로 UserDetails 타입을 세션으로 만든다

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(IllegalArgumentException::new);

        return new PrincipalDetails(user);
    }
}