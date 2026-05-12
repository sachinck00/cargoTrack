package org.jspiders.CourierAndLogisticsTrackingSystem.repositories;

import java.util.Optional;

import org.jspiders.CourierAndLogisticsTrackingSystem.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	@Query("select c from Customer c where c.customerEmail=?1")
	Optional<Customer> findCustomerWithEmail(String customerEmail);
	
	@Query("select c from Customer c where c.contact=?1")
	Optional<Customer> findCustomerWithContact(long contact);
}
