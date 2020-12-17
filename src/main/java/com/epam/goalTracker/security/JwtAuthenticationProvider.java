//package com.epam.goalTracker.security;
//
//import com.epam.sportbuddies.entity.enums.AuthProvider;
//import com.epam.sportbuddies.service.impl.UserDetailsServiceImpl;
//import com.epam.sportbuddies.util.JwtTokenTool;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//@Profile("security")
//public class JwtAuthenticationProvider implements AuthenticationProvider {
//
//    private final JwtTokenTool jwtTokenTool;
//
//    private final UserDetailsServiceImpl userDetailsService;
//
//    @Autowired
//    public JwtAuthenticationProvider(JwtTokenTool jwtTokenTool, UserDetailsServiceImpl userDetailsService) {
//        this.jwtTokenTool = jwtTokenTool;
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
//        String email = jwtTokenTool.getEmail((String) jwtAuthenticationToken.getCredentials());
//        String identifier = jwtTokenTool.getClaim((String) jwtAuthenticationToken.getCredentials(), "identifier");
//        String authProvider = jwtTokenTool.getClaim((String) jwtAuthenticationToken.getCredentials(), "authProvider");
//
//        UserDetails userDetails;
//        try {
//            if (authProvider.equalsIgnoreCase(AuthProvider.LOCAL.getId())) {
//                userDetails = userDetailsService.loadUserByUsername(email);
//            } else {
//                userDetails = userDetailsService.loadPersonByIdentifierId(identifier);
//            }
//        } catch (UsernameNotFoundException | UnsupportedOperationException e) {
//            throw new BadCredentialsException(e.getMessage());
//        }
//
//        jwtAuthenticationToken.setUserPrincipal(userDetails);
//        return jwtAuthenticationToken;
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//}
