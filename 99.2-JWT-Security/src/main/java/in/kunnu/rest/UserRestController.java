package in.kunnu.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.kunnu.binding.AuthRequest;
import in.kunnu.entity.UserEntity;
import in.kunnu.service.JwtService;
import in.kunnu.service.MyUserDetailsService;

@RestController
@RequestMapping("/api")
public class UserRestController {

	@Autowired
	private MyUserDetailsService service;
	
	@Autowired
	private JwtService jwt;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private AuthenticationManager authManager;

	// registration
	@PostMapping("/register")
	public String registerUser(@RequestBody UserEntity user) {
		String encodePwd = encoder.encode(user.getUpwd());
		user.setUpwd(encodePwd);
		boolean saveUser = service.saveUser(user);
		if (saveUser) {
			return "User Registered";
		} else {
			return "Registration failed";
		}
	}

	// login
	@PostMapping("/login")
	public String userAuthentication(@RequestBody AuthRequest r) {

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(r.getUname(), r.getPwd());

		try {
			Authentication auth = authManager.authenticate(token);// passing token to authenticate method
			//this will internally call authenticationProvider method also which will call MyUserDetailsService class
			//All this to check whether the username provided in login form exsist in DB or not.
			
			if (auth.isAuthenticated()) {// it return true of false
				String jwtToken = jwt.generateToken(r.getUname());
				return jwtToken;//in general we store this token into db but here just to show, we are returning token
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Authentication failed";
	}

	// welcome
	@GetMapping ("/welcome")
	public String welcomeMsg () {
		return "welcome Ayush";
	}
	
	@GetMapping ("/greet")
	public String greetMsg () {
		return "Good Morning";
	}

}
