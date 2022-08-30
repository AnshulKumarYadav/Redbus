package com.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.DTO.LoginDTO;
import com.app.model.Admin;
import com.app.model.Bus;
import com.app.model.CurrentAdminSession;
import com.app.service.AdminServiceImpl;
import com.app.service.BusServiceImpl;

@RestController
public class AdminController {
	
	
//	++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//	+                                  Admin Uri Control                                           +
//	++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	@Autowired
	private AdminServiceImpl adminServiceImpl;
	
	
	@PostMapping("/admin")
	public Admin registerAdmin(@Valid @RequestBody Admin admin)
	{
		return adminServiceImpl.registerAdmin(admin);
	}
	
	
	@PostMapping("/adminLogin")
	public CurrentAdminSession logIntoAccount(@RequestBody LoginDTO adminDTO)
	{
		return adminServiceImpl.logIntoAccount(adminDTO);
	}
	
	
	@PutMapping("/updateAdmin")
	public Admin updateAdmin(@RequestParam String key,@Valid @RequestBody Admin admin)
	{
		return adminServiceImpl.updateAdmin(admin, key);
	}
	
	@DeleteMapping("/deleteAdmin")
	public Admin deleteAdmin(@RequestParam String key)
	{
		return adminServiceImpl.deleteAdmin(key);
	}
	
	@PatchMapping("/adminLogout")
	public String logout(@RequestParam String key)
	{
		return adminServiceImpl.logOutFromAccount(key);
	}
	
	
	
	
	
	
//	++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//	+                                 Admin-Bus Uri Control                                        +
//	++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	@Autowired
	private BusServiceImpl busServiceImpl;
	
	@PostMapping("/addBus")
	public Bus addBus(@Valid @RequestBody Bus bus,@RequestParam String key)
	{
		return busServiceImpl.addBus(bus, key);
	}
	
	@PutMapping("/updateBus")
	public Bus updateBus(@Valid @RequestBody Bus bus,String key)
	{
		return busServiceImpl.updateBus(bus, key);
	}
	
	@DeleteMapping("/removeBus/{busNo}")
	public Bus removeBus(@PathVariable String busNo,@RequestParam String key)
	{
		return busServiceImpl.removeBus(busNo, key);
	}
	
	@GetMapping("getAllBus")
	public List<Bus> getAllBuses()
	{
		return busServiceImpl.getAllBus();
	}
	
	@GetMapping("/getBusBySourceAndDestination/{source}/{destination}")
	public List<Bus> getBusBySD(@PathVariable String source,@PathVariable String destination)
	{
		return busServiceImpl.getAllBusBySD(source, destination);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
