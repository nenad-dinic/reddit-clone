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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
        import javax.servlet.ServletException;
        import javax.servlet.ServletRequest;
        import javax.servlet.ServletResponse;
        import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {



    @Autowired
    private UserRepository userRepository;

    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String authHeader = request.getHeader("Authorization");
        String username;
        String token = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        } else {
            response.setStatus(401);
            return;
        }

        username = TokenUtils.getUsernameFromToken(token);

        if(username == null) {
            response.setStatus(401);
            return;
        }

        User u = userRepository.findOneByUsername(username);
        if(TokenUtils.validateToken(token, u)) {
            List <GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(TokenUtils.getRoleFromToken(token)));
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    u, null, authorities);
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }

    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/user/login") || path.startsWith("/user/register") || ((path.startsWith("/posts") || path.startsWith("/posts/community") || path.startsWith("/community") || path.startsWith("/uploads")) && request.getMethod().equals("GET"));
    }
}