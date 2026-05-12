package org.jspiders.CourierAndLogisticsTrackingSystem.services;

import java.util.List;
import java.util.Optional;

import org.jspiders.CourierAndLogisticsTrackingSystem.dto.ResponseStructure;
import org.jspiders.CourierAndLogisticsTrackingSystem.dto.ShipmentStatusType;
import org.jspiders.CourierAndLogisticsTrackingSystem.entities.Customer;
import org.jspiders.CourierAndLogisticsTrackingSystem.entities.Shipment;
import org.jspiders.CourierAndLogisticsTrackingSystem.exceptions.IdNotFoundException;
import org.jspiders.CourierAndLogisticsTrackingSystem.exceptions.InvalidContactNumberException;
import org.jspiders.CourierAndLogisticsTrackingSystem.exceptions.NoRecordFoundException;
import org.jspiders.CourierAndLogisticsTrackingSystem.repositories.CustomerRepository;
import org.jspiders.CourierAndLogisticsTrackingSystem.repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private ShipmentRepository shipmentRepo;
	
	public ResponseStructure<Customer> createCustomerInDatabase(Customer cust){
		if(String.valueOf(cust.getContact()).length()!=10) {
			throw new InvalidContactNumberException("Invalid Contact Number. Contact number must be 10 digits . .");
		}
		Customer savedCustomer = customerRepo.save(cust);
		ResponseStructure<Customer> res = new ResponseStructure<>();
		res.setData(savedCustomer);
		res.setMessage("1 record inserted into Customer table . .");
		res.setStatusCode(HttpStatus.CREATED.value());
		return res;
	}
	
	public ResponseStructure<List<Customer>> getAllCustomersFromDatabase(){
		List<Customer> li = customerRepo.findAll();
		ResponseStructure<List<Customer>> res = new ResponseStructure<>();
		res.setData(li);
		res.setMessage(li.size()+" records found in Customer table . .");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<Customer> getCustomerByIdFromDatabase(int id){
		Optional<Customer> opt = customerRepo.findById(id);
		if(opt.isEmpty()) {
			throw new IdNotFoundException("Record with id : "+id+", not exist in database . .");
		}
		ResponseStructure<Customer> res = new ResponseStructure<>();
		res.setData(opt.get());
		res.setMessage("Record found in Customer table . .");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<Customer> getCustomerByEmailFromDatabase(String customerEmail){
		Optional<Customer> opt = customerRepo.findCustomerWithEmail(customerEmail);
		
		if(opt.isEmpty()) {
			throw new NoRecordFoundException("Record with email : "+customerEmail+", not exist in database . .");
		}
		ResponseStructure<Customer> res = new ResponseStructure<>();
		res.setData(opt.get());
		res.setMessage("Record found in Customer table . .");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<Customer> getCustomerByContactFromDatabase(long contact){
		Optional<Customer> opt = customerRepo.findCustomerWithContact(contact);
		
		if(opt.isEmpty()) {
			throw new NoRecordFoundException("Record with contact : "+contact+", not exist in database . .");
		}
		ResponseStructure<Customer> res = new ResponseStructure<>();
		res.setData(opt.get());
		res.setMessage("Record found in Customer table . .");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<Customer> updateCustomerInDatabase(Customer cust){
		if(cust.getId()==null) {
			throw new IdNotFoundException("To update a record , id must be passed . .");
		}
		Optional<Customer> opt=customerRepo.findById(cust.getId());
		if(opt.isEmpty()) {
			throw new NoRecordFoundException("No record exist for id : "+cust.getId()+" that you have requested .");
		}
		Customer updatedCustomer=customerRepo.save(cust);
		ResponseStructure<Customer> res = new ResponseStructure<>();
		res.setData(updatedCustomer);
		res.setMessage("Record updated successfully for customer id : "+updatedCustomer.getId());
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	
	public ResponseStructure<Customer> deleteCustomerFromDatabaseById(int id){
		List<Shipment> li=shipmentRepo.findShipmentByCustomerId(id);
		ResponseStructure<Customer> res = new ResponseStructure<>();
		boolean isAllDelivered=true;
		if(li.size()!=0) {
			for(Shipment s:li) {
				if(s.getShipmentStatus() != ShipmentStatusType.DELIVERED) {
					isAllDelivered = false;
				}
			}	
		}
		if(isAllDelivered==false) {
			throw new IdNotFoundException("a shipment is active for that customer id : "+id+", cann't be deleted ");
		}
		
		for(Shipment s:li) {
			s.setCustomer(null);
		}
		customerRepo.deleteById(id);
		res.setData(null);
		res.setMessage("Record deleted successfully for customer id : "+id);
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	
}
