package in.kunnu.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.kunnu.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Integer> {

	public UserEntity findByUname(String uname);
	
}
