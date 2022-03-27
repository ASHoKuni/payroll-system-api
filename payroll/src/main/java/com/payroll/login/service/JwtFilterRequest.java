package com.payroll.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.payroll.login.utils.JwtUtil;

import java.io.IOException;

@Service
public class JwtFilterRequest extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        String authorizationHeader=request.getHeader("Authorization");
        String email=null;
        String jwtToken=null;
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer "))
        {
            jwtToken = authorizationHeader.substring((7));
            email = jwtUtil.extractUsername(jwtToken);

        }
        if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails currentUserDetails = userService.loadUserByUsername(email);
            Boolean tokenValidated = jwtUtil.validateToken(jwtToken,currentUserDetails);
            if(tokenValidated){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(currentUserDetails,null,currentUserDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}



