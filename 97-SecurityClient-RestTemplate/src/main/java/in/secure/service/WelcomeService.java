package in.secure.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WelcomeService {

	private String url = "http://localhost:8080/";
	
	public void m1 () {
		
		RestTemplate rt = new RestTemplate();
		
		HttpHeaders hh = new HttpHeaders();
		hh.setBasicAuth("Kunnu","Bhai");
		
		HttpEntity<String> httpEntity = new HttpEntity<>(hh);
		ResponseEntity<String> re = rt.exchange(url	//url
									,HttpMethod.GET	//method (get or post)
									,httpEntity		//object
									,String.class);	//getting formate

		//ResponseEntity<String> re = rt.getForEntity(url, String.class);
		
		String s = re.getBody();
		System.out.println(s);
	}	
}
