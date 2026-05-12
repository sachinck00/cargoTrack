package org.jspiders.CourierAndLogisticsTrackingSystem.controllers;

import java.util.List;

import org.jspiders.CourierAndLogisticsTrackingSystem.dto.ResponseStructure;
import org.jspiders.CourierAndLogisticsTrackingSystem.entities.Customer;
import org.jspiders.CourierAndLogisticsTrackingSystem.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/test")
	public String testAPI() {
		return "Welcome to app . . ";
	}

	@PostMapping("/add")
	public ResponseEntity<ResponseStructure<Customer>> createCustomer(@RequestBody Customer cust){
		return new ResponseEntity<ResponseStructure<Customer>>(customerService.createCustomerInDatabase(cust), HttpStatus.CREATED);
	}
	
	@GetMapping("/")
	public ResponseEntity<ResponseStructure<List<Customer>>> getAllCustomers(){
		return new ResponseEntity<ResponseStructure<List<Customer>>>(customerService.getAllCustomersFromDatabase(), HttpStatus.FOUND);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<ResponseStructure<Customer>> getCustomerById(@PathVariable int id){
		return new ResponseEntity<ResponseStructure<Customer>>(customerService.getCustomerByIdFromDatabase(id), HttpStatus.FOUND);
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<ResponseStructure<Customer>> getCustomerByEmail(@PathVariable String email){
		return new ResponseEntity<ResponseStructure<Customer>>(customerService.getCustomerByEmailFromDatabase(email), HttpStatus.FOUND);
	}
	
	@GetMapping("/contact/{contact}")
	public ResponseEntity<ResponseStructure<Customer>> getCustomerByContact(@PathVariable long contact){
		return new ResponseEntity<ResponseStructure<Customer>>(customerService.getCustomerByContactFromDatabase(contact), HttpStatus.FOUND);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseStructure<Customer>> updateCustomerDetails(@RequestBody Customer cust){
		return new ResponseEntity<ResponseStructure<Customer>>(customerService.updateCustomerInDatabase(cust), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<Customer>> deleteCustomerById(@PathVariable int id){
		return new ResponseEntity<ResponseStructure<Customer>>(customerService.deleteCustomerFromDatabaseById(id), HttpStatus.OK);
	}
	
	
}
