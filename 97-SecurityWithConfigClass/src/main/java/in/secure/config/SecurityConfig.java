package in.secure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	 @Bean
     public InMemoryUserDetailsManager inMemoryUsers() {
             
             UserDetails ayushUser = User.withDefaultPasswordEncoder()
                                                                  .username("ayush")
                                                                  .password("ayush")
                                                                  .authorities("ADMIN") //or role() method also we can use
                                                                   .build();
             
             
             UserDetails kunnuUser = User.withDefaultPasswordEncoder()
                                                                     .username("kunnu")
                                                                     .password("kunnu")
                                                                     .authorities("USER")
                                                                     .build();
             
             return new InMemoryUserDetailsManager(ayushUser, kunnuUser);
             
     }

	 @Bean
     SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
             http.authorizeHttpRequests((authorize) -> authorize
            		 																	.requestMatchers("/contact", "/swagger-ui/index.html#")
            		 																	.permitAll() //no roles, everyone can access
            		 																	.anyRequest()
            		 																	.authenticated() )
                          // .formLogin();
             				.httpBasic(withDefaults())//this is a static import, write is manually
                            .formLogin(withDefaults());
             return http.build();
     }
//in the requestMatchers, we are giving the urls as parameters which we want to allow all permitions
}
