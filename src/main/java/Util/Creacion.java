package Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import spring.enums.RolNombre;
import spring.modelo.Ciudad;
import spring.modelo.Rol;
import spring.serviceImpl.CiudadService;
import spring.serviceImpl.RolService;

public class Creacion  implements CommandLineRunner  {
	
	@Autowired
	RolService rolService;
	@Autowired
	CiudadService ciudadService;
	  
	@Override
	public void run(String... args) throws Exception {
	  
	//
	Ciudad ciudad = new Ciudad("08:00", "20:00", 10);
	ciudadService.save(ciudad);
	
	if (rolService.findAll().isEmpty()) {
		Rol roleUser = new Rol(RolNombre.ROLE_USER);
	    rolService.save(roleUser);
	}
	}
	  
	  
	 
	  
}
	  
