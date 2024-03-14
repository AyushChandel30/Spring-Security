package in.kunnu.config;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {//do not use "SecurityConfig" as class name
	
    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";

    @Autowired
    private DataSource dataSource;
    //to communicate with db, we need dataSource Object 
    //(just like we need JpaRepository object when we use spring data JPA)

    @Autowired
    public void authManager(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()//use jdbc authentication
            .dataSource(dataSource)//use dataSource to communicate with db
            .passwordEncoder(new BCryptPasswordEncoder())//incrypt the password comming from login form
            .usersByUsernameQuery("select username,password,enabled from users where username=?")
//use users (table name) by username (column name) & in parameter "query to retrieve the record from user table"
            .authoritiesByUsernameQuery("select username,authority from authorities where username=?");
//use authorities (table name) by username (column name) & in parameter query to retrieve role for the user record (for autherization) 
    }		
    
    @Bean
    SecurityFilterChain securityConfig(HttpSecurity http) throws Exception {
                    
            http.authorizeHttpRequests( (req) -> req
                            .requestMatchers("/admin").hasRole(ADMIN)//only ADMIN (role name) can access url with /admin
                            .requestMatchers("/user").hasAnyRole(ADMIN,USER) //AMIN and USER can access url with /user
                            .requestMatchers("/").permitAll() // everyone can access this.
                            .anyRequest().authenticated()//any other url can be access by user and almin both
            ).formLogin(withDefaults());//make login form
            
            return http.build();
    }

}
