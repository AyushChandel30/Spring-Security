package in.secure.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeRestController {

	@GetMapping ("/")
	public String demo () {
		return "Welcome to security from Develop";
	}
	
	@GetMapping ("/check")
	public String demo2 () {
		return "wait... Checking...";
	}
	
	@GetMapping ("/contact")
	public String contect () {
		return "My contact is : 7987891696";
	}
 	
}
