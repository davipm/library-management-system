//package com.example.librarymanagementsystem.security;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//import jakarta.annotation.PostConstruct;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.crypto.SecretKey;
//import java.io.IOException;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class JwtTokenProvider extends OncePerRequestFilter {
//
//    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
//
//    public static final String ROLES = "roles";
//    public static final String BEARER_PREFIX = "Bearer ";
//    public static final String AUTHORIZATION_HEADER = "Authorization";
//
//    @Value("${jwt.secret}")
//    private String jwtSecret;
//
//    @Value("${jwt.expiration}")
//    private int jwtExpiration;
//
//    private SecretKey key;
//    private JwtParser jwtParser;
//
//    @PostConstruct
//    public void init() {
//        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
//        this.jwtParser = Jwts.parser().setSigningKey(this.key).build();
//    }
//
//    public String generateToken(String username, List<String> roles) {
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + jwtExpiration);
//
//        return Jwts.builder()
//                .setSubject(username)
//                .claim(ROLES, roles)
//                .setIssuedAt(now)
//                .setExpiration(expiryDate)
//                .signWith(key, SignatureAlgorithm.HS512)
//                .compact();
//    }
//
//    public String getUsernameFromJWT(String token) {
//        Claims claims = jwtParser.parseClaimsJws(token).getBody();
//        return claims.getSubject();
//    }
//
//    public List<String> getRolesFromJWT(String token) {
//        Claims claims = jwtParser.parseClaimsJws(token).getBody();
//        return (List<String>) claims.get(ROLES);
//    }
//
//    public boolean validateToken(String authToken) {
//        try {
//            jwtParser.parseClaimsJws(authToken);
//            return true;
//        } catch (JwtException | IllegalArgumentException e) {
//            logger.error("Invalid JWT token: {}", e.getMessage());
//        }
//        return false;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        try {
//            String jwt = getJwtFromRequest(request);
//
//            if (StringUtils.hasText(jwt) && validateToken(jwt)) {
//                String username = getUsernameFromJWT(jwt);
//                List<String> roles = getRolesFromJWT(jwt);
//
//                List<SimpleGrantedAuthority> authorities = roles.stream()
//                        .map(SimpleGrantedAuthority::new)
//                        .collect(Collectors.toList());
//
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                        username, null, authorities);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        } catch (Exception ex) {
//            logger.error("Could not set user authentication in security context", ex);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private String getJwtFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
//            return bearerToken.substring(BEARER_PREFIX.length());
//        }
//        return null;
//    }
//}