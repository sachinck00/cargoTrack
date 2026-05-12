package org.jspiders.CourierAndLogisticsTrackingSystem.services;


import java.util.List;
import java.util.Optional;

import org.jspiders.CourierAndLogisticsTrackingSystem.dto.ResponseStructure;
import org.jspiders.CourierAndLogisticsTrackingSystem.dto.ShipmentStatusType;
import org.jspiders.CourierAndLogisticsTrackingSystem.entities.Shipment;
import org.jspiders.CourierAndLogisticsTrackingSystem.entities.WareHouse;
import org.jspiders.CourierAndLogisticsTrackingSystem.exceptions.IdNotFoundException;
import org.jspiders.CourierAndLogisticsTrackingSystem.exceptions.InvalidContactNumberException;
import org.jspiders.CourierAndLogisticsTrackingSystem.exceptions.InvalidInputException;
import org.jspiders.CourierAndLogisticsTrackingSystem.exceptions.NoRecordFoundException;
import org.jspiders.CourierAndLogisticsTrackingSystem.repositories.ShipmentRepository;
import org.jspiders.CourierAndLogisticsTrackingSystem.repositories.WareHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class WareHouseService {

	@Autowired
	private WareHouseRepository wareHouseRepo;
	
	@Autowired
	private ShipmentRepository shipmentRepo;
	
	public ResponseStructure<WareHouse> createWareHouseInDatabase(WareHouse house){
		if(String.valueOf(house.getContact()).length()!=10) {
			throw new InvalidContactNumberException("Invalid Contact Number. Contact number must be 10 digits . .");
		}
		WareHouse savedHouse = wareHouseRepo.save(house);
		ResponseStructure<WareHouse> res = new ResponseStructure<>();
		res.setData(savedHouse);
		res.setMessage("1 record inserted into ware house table");
		res.setStatusCode(HttpStatus.CREATED.value());
		return res;
		
	}
	
	public ResponseStructure<List<WareHouse>> getAllWareHouses() {
		List<WareHouse> warehouses = wareHouseRepo.findAll();
	    ResponseStructure<List<WareHouse>> res =new ResponseStructure<>();
	    res.setData(warehouses);
	    res.setMessage(warehouses.size()+" Warehouses records fetched successfully");
	    res.setStatusCode(HttpStatus.FOUND.value());
	    return res;
	}
	
	public ResponseStructure<WareHouse> getWareHouseById(int id) {
		Optional<WareHouse> opt =  wareHouseRepo.findById(id);
		if(opt.isEmpty()) {
			throw new IdNotFoundException("Record with ware house id : "+id+" not exist in database");
		}
	    WareHouse wareHouse = opt.get();
	    ResponseStructure<WareHouse> res = new ResponseStructure<>();
	    res.setData(wareHouse);
	    res.setMessage("Record with ware house id "+id+" found in databse");
	    res.setStatusCode(HttpStatus.FOUND.value());
	    return res;
	}
	
	public ResponseStructure<WareHouse> getWareHouseByLocation(String loc) {
		Optional<WareHouse> opt =  wareHouseRepo.findWareHouseByLocation(loc);
		if(opt.isEmpty()) {
			throw new NoRecordFoundException("Record with ware house location : "+loc+" not exist in database");
		}
	    WareHouse wareHouse = opt.get();
	    ResponseStructure<WareHouse> res = new ResponseStructure<>();
	    res.setData(wareHouse);
	    res.setMessage("Record with ware house location : "+loc+" found in databse");
	    res.setStatusCode(HttpStatus.FOUND.value());
	    return res;
	}
	
	public ResponseStructure<List<WareHouse>> getWareHousesByCapacityGreaterThan(int capacity) {
	    List<WareHouse> list = wareHouseRepo.findWareHousesByCapacityGreaterThan(capacity);
	    if (list.isEmpty()) {
	        throw new NoRecordFoundException("No warehouses exist with capacity greater than " + capacity+" in database . .");
	    }
	    ResponseStructure<List<WareHouse>> res = new ResponseStructure<>();
	    res.setData(list);
	    res.setMessage(list.size()+ " Warehouses fetched successfully");
	    res.setStatusCode(HttpStatus.FOUND.value());
	    return res;
	}
	
	public ResponseStructure<WareHouse> updateWareHouse(WareHouse w){
		if(w.getId() == null) {
			throw new IdNotFoundException("Id must be pass to upadate a record");
		}
		Optional<WareHouse> opt = wareHouseRepo.findById(w.getId());
		if(opt.isEmpty()) {
			throw new NoRecordFoundException("No record exist with warehouse id : "+w.getId()+ " in database . .");
		}
		WareHouse updatedWareHouse = wareHouseRepo.save(w);
		ResponseStructure<WareHouse> res = new ResponseStructure<>();
	    res.setData(updatedWareHouse);
	    res.setMessage("Record updated in databse");
	    res.setStatusCode(HttpStatus.OK.value());
	    return res;
	}
	
	public ResponseStructure<WareHouse> deleteWareHouse(int id){
		List<Shipment> li = shipmentRepo.findShipmentsByWareHouseId(id);
		boolean isAllDelivered = true;
		for(Shipment s:li) {
			if(s.getShipmentStatus()!=ShipmentStatusType.DELIVERED) {
				isAllDelivered = false;
			}
		}
		if(isAllDelivered == false) {
			throw new InvalidInputException("a shipment is active in ware house , cannt be deleted");
		}
		
		for(Shipment s:li) {
			s.setWareHouse(null);
		}
		
		shipmentRepo.saveAll(li);
		wareHouseRepo.deleteById(id);
		ResponseStructure<WareHouse> res = new ResponseStructure<>();
	    res.setData(null);
	    res.setMessage("Record deleted from databse");
	    res.setStatusCode(HttpStatus.OK.value());
	    return res;
		
	}
}
