package hansung.com.sample_project.config.filter;

import hansung.com.sample_project.provider.JwtTokenProvider;
import hansung.com.sample_project.service.UserDetailsService;
import hansung.com.sample_project.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private static final String SESSION_ID = "Token";
    private static final String[] whitelist = {"/", "/login", "/logout", "/review", "/hello"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtFromRequest(request);
        String userId = null;
        if(jwt != null && JwtTokenProvider.validate(jwt)){
            Claims claims = JwtTokenProvider.getClaims(jwt);
            userId = claims.get("userId", String.class);

        } else {
            filterChain.doFilter(request, response);
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            System.out.println("#################");
            System.out.println("##TOKEN_ACCESS###");
            System.out.println("#################");
            filterChain.doFilter(request, response);
        }

    }

    /**
     * 화이트 리스트의 경우 인증 체크 안함
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

    private String getJwtFromRequest(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        System.out.println("###############HEADER_TOKEN:" +token);
        if(StringUtils.hasText(token) && token.startsWith("Bearer "))
            return token.substring("Bearer ".length());
        return null;
    }
}