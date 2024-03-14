package in.kunnu.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import in.kunnu.service.MyUserdetailService;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

	@Autowired
	private MyUserdetailService userdtlService;

	@Bean// converting normal pwd to encoded pwd. We can create our oun also
	public PasswordEncoder pwdEncoder() {
		return new BCryptPasswordEncoder();
	}//In the previous application, we have written this directly in the registration method.

	/*
	 * In this method, we are acually telling the spring security form where User
	 * data it have to retrieve for the authentication perpuse. Basically
	 * redirecting to MyUserdetailService class
	 */
//	public void configureUsers(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userdtlService)// use userdtlService to retrieve the data
//				.passwordEncoder(pwdEncoder());// encode the pwd before comparing
//	}Update: above method done by below method, so need to write this one

	/* this is a authentication provider method which is used to provide the details
	 * required to perform authentication. It also do the above commented method work*/
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userdtlService);
		authenticationProvider.setPasswordEncoder(pwdEncoder());
		return authenticationProvider;
	}
	
	/* this is authentication method which is used to do actuall authentication
	 * Authentication manager we need it to pass to login method. It will take the login detail in the form of
	 * tockens and do not take Customer object direct (see cotroller login method)*/
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

	@Bean
	public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
		return http.csrf().disable().authorizeRequests().anyRequest().permitAll().and().build();
	}

}
