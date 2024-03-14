package in.kunnu.repo;

import org.springframework.data.repository.CrudRepository;

import in.kunnu.entiry.Customer;

public interface CustomerRepo extends CrudRepository<Customer, Integer> {
	
	public Customer findByName(String name);

}
