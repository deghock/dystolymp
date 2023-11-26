package ru.spbu.distolymp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.spbu.distolymp.service.StaffDetailsService;

import java.util.Collections;

@Component
public class AuthProviderImpl implements AuthenticationProvider {
    private final StaffDetailsService staffDetailsService;

    @Autowired
    public AuthProviderImpl(StaffDetailsService staffDetailsService) {
        this.staffDetailsService = staffDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        UserDetails staffDetails =  staffDetailsService.loadUserByUsername(login);
        String password = authentication.getCredentials().toString();
        if (!password.equals(staffDetails.getPassword()))
            throw new BadCredentialsException("Incorrect password");
        return new UsernamePasswordAuthenticationToken(staffDetails, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
