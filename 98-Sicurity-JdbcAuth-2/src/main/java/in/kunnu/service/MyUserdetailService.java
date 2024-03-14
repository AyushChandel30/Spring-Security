package in.kunnu.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.kunnu.entiry.Customer;
import in.kunnu.repo.CustomerRepo;

@Service
public class MyUserdetailService implements UserDetailsService {

	@Autowired
	private CustomerRepo cRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer = cRepo.findByName(username);
		return new User(customer.getName(), customer.getPwd(), Collections.emptyList());
	}//																									since there is no role right now

}// this class is used to retrieve the User recored.
