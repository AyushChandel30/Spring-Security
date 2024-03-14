package in.kunnu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table (name="ayush_users")
public class UserEntity {

	@Id
	@GeneratedValue
	private Integer userId;
	private String uname;
	private String upwd;
	private Long phno;
	
}
