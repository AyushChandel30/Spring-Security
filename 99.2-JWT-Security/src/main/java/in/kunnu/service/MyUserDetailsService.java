package in.kunnu.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.kunnu.entity.UserEntity;
import in.kunnu.repo.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo repo;
	
	@Override//to retrieve user recored based on user name.
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity entity = repo.findByUname(username);
		return new User(entity.getUname(), entity.getUpwd(), Collections.emptyList());
	}

	//to save user to db
	public boolean saveUser (UserEntity user) {
		return repo.save(user).getUserId()!=null;
	}

}
