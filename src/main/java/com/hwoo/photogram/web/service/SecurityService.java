package com.hwoo.photogram.web.service;

import com.hwoo.photogram.config.auth.PrincipalDetails;
import com.hwoo.photogram.domain.user.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public void reAuthenticate(User user) {
        PrincipalDetails principalDetails = new PrincipalDetails(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principalDetails, null, principalDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}