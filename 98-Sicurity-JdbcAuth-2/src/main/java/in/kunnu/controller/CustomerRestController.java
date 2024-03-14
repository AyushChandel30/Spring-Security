package in.kunnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.kunnu.entiry.Customer;
import in.kunnu.repo.CustomerRepo;

@RestController
public class CustomerRestController {

	@Autowired
	private CustomerRepo cRepo;
	
	@Autowired
	private PasswordEncoder pwdEncoder;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@PostMapping ("/login")
	public ResponseEntity<String> loginCheck (@RequestBody Customer c) {
		UsernamePasswordAuthenticationToken token = 
				new UsernamePasswordAuthenticationToken(c.getName(), c.getPwd());
		try {
			Authentication authenticate = authManager.authenticate(token);
			//authenticate method do not take Customer object directly
			if (authenticate.isAuthenticated()) {
				return new ResponseEntity<String> ("Welcome", HttpStatus.OK);
			}
		} catch (Exception e) {
			//logger
		}
			return new ResponseEntity<String> ("Invalid credentials", HttpStatus.BAD_REQUEST);
	}

	
	@PostMapping ("/register")
	public String registerCustomer (@RequestBody Customer customer) {
		//duplicate check
		String encodePwd = pwdEncoder.encode(customer.getPwd());
		customer.setPwd(encodePwd);
		cRepo.save(customer);
		return "registration completed";
	}//some part of this method can be transfred to service class
	
	@GetMapping ("/tutu")
	public String registerwCustomer (Customer customer) {
		return "tutu registration completed";
	}
	
	
	
}
