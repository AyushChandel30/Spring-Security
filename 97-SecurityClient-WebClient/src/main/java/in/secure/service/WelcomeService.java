package in.secure.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WelcomeService {

	private String url = "http://localhost:8080/";
	
	public void m1 () {
		
		WebClient wC = WebClient.create();
		
		String block = wC.get()
						.uri(url)
						.headers(h->h.setBasicAuth("Kunnu", "Bhai"))
						.retrieve()
						.bodyToMono(String.class)
						.block();
		
		System.out.println(block);
		
		
	}
	
}
