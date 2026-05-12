package org.jspiders.CourierAndLogisticsTrackingSystem.controllers;

import java.util.List;

import org.jspiders.CourierAndLogisticsTrackingSystem.dto.ResponseStructure;
import org.jspiders.CourierAndLogisticsTrackingSystem.entities.DeliveryAgent;
import org.jspiders.CourierAndLogisticsTrackingSystem.services.DeliveryAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveryAgent")
public class DeliveryAgentController {
	
	@Autowired
	private DeliveryAgentService deliveryAgentService;
	
	@PostMapping("/add")
	public ResponseEntity<ResponseStructure<DeliveryAgent>> createDeliveryAgent(@RequestBody DeliveryAgent agent){
		return new ResponseEntity<ResponseStructure<DeliveryAgent>>(deliveryAgentService.createDeliveryAgentInDatabase(agent), HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity<ResponseStructure<List<DeliveryAgent>>> getAllAgents(){
		return new ResponseEntity<ResponseStructure<List<DeliveryAgent>>>(deliveryAgentService.getAllDeliveryAgents(),HttpStatus.FOUND);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<ResponseStructure<DeliveryAgent>> getAgentById(@PathVariable int id){
		return new ResponseEntity<ResponseStructure<DeliveryAgent>>(deliveryAgentService.getDeliveryAgentById(id),HttpStatus.FOUND);
	}
	
	@GetMapping("/vehicleNumber/{vehicleNumber}")
	public ResponseEntity<ResponseStructure<DeliveryAgent>> getAgentByVehicleNumber(@PathVariable("vehicleNumber") String vn){
		return new ResponseEntity<ResponseStructure<DeliveryAgent>>(deliveryAgentService.getDeliveryAgentByVehicleNumber(vn),HttpStatus.FOUND);
	}
	
	@GetMapping("/contact/{contact}")
	public ResponseEntity<ResponseStructure<DeliveryAgent>> getAgentByContact(@PathVariable long contact){
		return new ResponseEntity<ResponseStructure<DeliveryAgent>>(deliveryAgentService.getDeliveryAgentByContact(contact),HttpStatus.FOUND);
	}
	
	@GetMapping("/rating/{rating}")
	public ResponseEntity<ResponseStructure<List<DeliveryAgent>>> getAgentByRatingGreaterThan(@PathVariable double rating){
		return new ResponseEntity<ResponseStructure<List<DeliveryAgent>>>(deliveryAgentService.getDeliveryAgentsByRatingGreaterThan(rating),HttpStatus.FOUND);
	}
	
	@PatchMapping("/update")
	public ResponseEntity<ResponseStructure<DeliveryAgent>> updateDeliveryAgent(@RequestBody DeliveryAgent da){
		return new ResponseEntity<ResponseStructure<DeliveryAgent>>(deliveryAgentService.updateAgentInDatabase(da),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<DeliveryAgent>> deleteAgentById(@PathVariable int id){
		return new ResponseEntity<ResponseStructure<DeliveryAgent>>(deliveryAgentService.deleteAgentFromDatabase(id),HttpStatus.FOUND);
	}
	@PatchMapping("/updateAvailabilityStatus/{id}/{status}")
	public ResponseEntity<ResponseStructure<DeliveryAgent>> updateAvailability(@PathVariable int id, @PathVariable("status") boolean availabilityStatus) {
		return new ResponseEntity<>(deliveryAgentService.updateAvailability(id, availabilityStatus),HttpStatus.OK);
	}
}
