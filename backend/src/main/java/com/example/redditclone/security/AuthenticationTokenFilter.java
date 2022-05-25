package com.example.redditclone.security;

import com.example.redditclone.model.User;
import com.example.redditclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
        import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.security.core.userdetails.UserDetailsService;
        import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
        import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

        import javax.servlet.FilterChain;
        import javax.servlet.ServletException;
        import javax.servlet.ServletRequest;
        import javax.servlet.ServletResponse;
        import javax.servlet.http.HttpServletRequest;
        import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    //@Autowired
    //private  UserDetailsService userDetailsService;

    private UserRepository userRepository;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("Authorization");
        if(token != null){
            if(token.startsWith("Bearer ")){
                token = token.substring(7);
            }
        }

        String username = TokenUtils.getUsernameFromToken(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            User user = userRepository.findOneByUsername(username);
            if (TokenUtils.validateToken(token, user)) {
                List <GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("admin"));
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        user, null, authorities);
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(request, response);
    }
}