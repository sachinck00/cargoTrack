package org.jspiders.CourierAndLogisticsTrackingSystem.controllers;

import java.util.List;

import javax.sound.midi.Patch;

import org.jspiders.CourierAndLogisticsTrackingSystem.dto.ResponseStructure;
import org.jspiders.CourierAndLogisticsTrackingSystem.entities.WareHouse;
import org.jspiders.CourierAndLogisticsTrackingSystem.services.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/wareHouse")
public class WareHouseController {
	
	@Autowired
	private WareHouseService wareHouseService;
	
	@PostMapping("/add")
	public ResponseEntity<ResponseStructure<WareHouse>> createWareHouse(@RequestBody WareHouse house){
		return new ResponseEntity<ResponseStructure<WareHouse>>(wareHouseService.createWareHouseInDatabase(house), HttpStatus.CREATED);
	}
	
	@GetMapping("/")
	public ResponseEntity<ResponseStructure<List<WareHouse>>> getAllWareHouses() {
	    return new ResponseEntity<ResponseStructure<List<WareHouse>>>( wareHouseService.getAllWareHouses(),HttpStatus.FOUND);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<ResponseStructure<WareHouse>> getWareHouseById(@PathVariable int id) {
	    return new ResponseEntity<ResponseStructure<WareHouse>>( wareHouseService.getWareHouseById(id), HttpStatus.FOUND);
	}
	
	@GetMapping("/location/{location}")
	public ResponseEntity<ResponseStructure<WareHouse>> getWareHouseByILocation(@PathVariable("location") String loc) {
	    return new ResponseEntity<ResponseStructure<WareHouse>>(wareHouseService.getWareHouseByLocation(loc), HttpStatus.FOUND);
	}
	
	@GetMapping("/capacityGreaterThan/{capacity}")
	public ResponseEntity<ResponseStructure<List<WareHouse>>> getWareHousesByCapacityGreateThan(@PathVariable int capacity) {
	    return new ResponseEntity<ResponseStructure<List<WareHouse>>>(wareHouseService.getWareHousesByCapacityGreaterThan(capacity),HttpStatus.OK);
	}
	
	@PatchMapping("/update")
	public ResponseEntity<ResponseStructure<WareHouse>> updateWareHouse(@RequestBody WareHouse w) {
	    return new ResponseEntity<ResponseStructure<WareHouse>> (wareHouseService.updateWareHouse(w), HttpStatus.OK);
	} 
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<WareHouse>> deleteWareHouseById(@PathVariable int id) {
	    return new ResponseEntity<ResponseStructure<WareHouse>>(wareHouseService.deleteWareHouse(id), HttpStatus.OK);
	}
}
