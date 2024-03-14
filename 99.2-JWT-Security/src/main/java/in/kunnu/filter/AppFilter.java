package in.kunnu.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import in.kunnu.service.JwtService;
import in.kunnu.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AppFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwt;
	
	@Autowired
	private MyUserDetailsService userDtlsSvc;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = request.getHeader("Authorization");//token coming from postman, "Authorization" is key and token is value
		String username = null;
		
		if (token != null && token.startsWith("Bearer")) {//token coming from postman have "Bearer <token>" structure
			token = token.substring(7);//skipping 1st 7 indexes from "Bearer <token>", basically getting real token
			username = jwt.extractUsername(token);
		}
		
		if(username != null && 
				SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDtlsSvc.loadUserByUsername(username);
            if(jwt.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }	
		filterChain.doFilter(request, response);
	}
}
