package org.jspiders.CourierAndLogisticsTrackingSystem.services;

import java.util.List;
import java.util.Optional;

import org.jspiders.CourierAndLogisticsTrackingSystem.dto.ResponseStructure;
import org.jspiders.CourierAndLogisticsTrackingSystem.dto.ShipmentStatusType;
import org.jspiders.CourierAndLogisticsTrackingSystem.entities.Customer;
import org.jspiders.CourierAndLogisticsTrackingSystem.entities.DeliveryAgent;
import org.jspiders.CourierAndLogisticsTrackingSystem.entities.Shipment;
import org.jspiders.CourierAndLogisticsTrackingSystem.exceptions.IdNotFoundException;
import org.jspiders.CourierAndLogisticsTrackingSystem.exceptions.InvalidContactNumberException;
import org.jspiders.CourierAndLogisticsTrackingSystem.exceptions.NoRecordFoundException;
import org.jspiders.CourierAndLogisticsTrackingSystem.repositories.DeliveryAgentRepository;
import org.jspiders.CourierAndLogisticsTrackingSystem.repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DeliveryAgentService {
	
	@Autowired
	private ShipmentRepository shipmentRepo;
	@Autowired
	private DeliveryAgentRepository deliveryAgentRepo;
	
	public ResponseStructure<DeliveryAgent> createDeliveryAgentInDatabase(DeliveryAgent agent){
		if(String.valueOf(agent.getContact()).length()!=10) {
			throw new InvalidContactNumberException("Invalid Contact Number. Contact number must be 10 digits . .");
		}
		DeliveryAgent savedAgent = deliveryAgentRepo.save(agent);
		ResponseStructure<DeliveryAgent> res = new ResponseStructure<>();
		res.setData(savedAgent);
		res.setMessage("1 recored inserted into DeliveryAgent table . .");
		res.setStatusCode(HttpStatus.CREATED.value());
		return res;
	}
	
	public ResponseStructure<List<DeliveryAgent>> getAllDeliveryAgents(){
		List<DeliveryAgent> li=deliveryAgentRepo.findAll();
		ResponseStructure<List<DeliveryAgent>> res = new ResponseStructure<>();
		res.setData(li);
		res.setMessage(li.size()+" records found in Database . .");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<DeliveryAgent> getDeliveryAgentById(int id){
		Optional<DeliveryAgent> opt=deliveryAgentRepo.findById(id);
		if(opt.isEmpty()) {
			throw new IdNotFoundException("No record found for Delivery agent id: " + id);
		}
		ResponseStructure<DeliveryAgent> res = new ResponseStructure<>();
		res.setData(opt.get());
		res.setMessage("record with id : "+id+" found in Database . .");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<DeliveryAgent> getDeliveryAgentByVehicleNumber(String vn){
		Optional<DeliveryAgent> opt=deliveryAgentRepo.findAgentByVehicleNumber(vn);
		if(opt.isEmpty()) {
			throw new NoRecordFoundException("No record exist for Delivery agent vehicle number : " + vn);
		}
		ResponseStructure<DeliveryAgent> res = new ResponseStructure<>();
		res.setData(opt.get());
		res.setMessage("Record found with Vehicle number : "+vn+" in Database . .");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<DeliveryAgent> getDeliveryAgentByContact(long contact){
		Optional<DeliveryAgent> opt=deliveryAgentRepo.findAgentByContact(contact);
		if(opt.isEmpty()) {
			throw new NoRecordFoundException("No record exist for Delivery agent contact : " + contact);
		}
		ResponseStructure<DeliveryAgent> res = new ResponseStructure<>();
		res.setData(opt.get());
		res.setMessage("Record found with contact number : "+contact+" in Database . .");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<List<DeliveryAgent>> getDeliveryAgentsByRatingGreaterThan(double rating){
		List<DeliveryAgent> li=deliveryAgentRepo.findAgentsByRatingGreaterThan(rating);
		if(li.size()==0) {
			throw new NoRecordFoundException("No agent record found for rating greater than "+rating);
		}
		ResponseStructure<List<DeliveryAgent>> res = new ResponseStructure<>();
		res.setData(li);
		res.setMessage(li.size()+" Records found in Database . .");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<DeliveryAgent> updateAgentInDatabase(DeliveryAgent da){
		if(da.getId()==null) {
			throw new IdNotFoundException("To update , id must be passed ");
		}
		Optional<DeliveryAgent> opt=deliveryAgentRepo.findById(da.getId());
		if(opt.isEmpty()) {
			throw new NoRecordFoundException("Record with Id :" +da.getId()+" not exist in database");
		}
		/*Optional<DeliveryAgent> existingAgent = deliveryAgentRepo.findAgentByContact(opt.get().getContact());
		if(existingAgent.isPresent()) {
			throw new InvalidContactNumberException("Contact Number ALready exist .");
		}*/
		DeliveryAgent updatedAgent = deliveryAgentRepo.save(da);
		ResponseStructure<DeliveryAgent> res = new ResponseStructure<>();
		res.setData(updatedAgent);
		res.setMessage("Record updated for id : "+updatedAgent.getId()+" successfully in Database . .");
		res.setStatusCode(HttpStatus.OK.value());
		return res;
		
	}
	
	public ResponseStructure<DeliveryAgent> deleteAgentFromDatabase(int id){
		List<Shipment> li=shipmentRepo.findShipmentByDeliveryAgentId(id);
		ResponseStructure<DeliveryAgent> res = new ResponseStructure<>();
		boolean isAllDelivered=true;
		if(li.size()!=0) {
			for(Shipment s:li) {
				if(s.getShipmentStatus() != ShipmentStatusType.DELIVERED) {
					isAllDelivered = false;
				}
			}	
		}
		if(isAllDelivered==false) {
			throw new IdNotFoundException("a shipment is active for that delivery agent id : "+id+", cann't be deleted ");
		}
		
		for(Shipment s : li) {
			s.setDeliveryAgent(null);
		}
		shipmentRepo.saveAll(li);
		deliveryAgentRepo.deleteById(id);
		res.setData(null);
		res.setMessage("Record deleted successfully for customer id : "+id);
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	
	public ResponseStructure<DeliveryAgent> updateAvailability(int id, boolean status) {
	    Optional<DeliveryAgent> opt = deliveryAgentRepo.findById(id);
	    if(opt.isEmpty()) {
	    	throw new NoRecordFoundException("No record found for Delivery agent id: " + id);
	    }
	    DeliveryAgent da=opt.get();
	    da.setAvailability(status);

	    DeliveryAgent updatedAgent = deliveryAgentRepo.save(da);

	    ResponseStructure<DeliveryAgent> res = new ResponseStructure<>();
	    res.setData(updatedAgent);
	    res.setMessage("Agent Availability updated successfully");
	    res.setStatusCode(HttpStatus.OK.value());
	    return res;
	}
}
