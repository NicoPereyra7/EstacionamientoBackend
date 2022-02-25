package spring.controllers;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.dto.CiudadDTO;
import spring.modelo.Ciudad;
import spring.serviceImpl.CiudadService;

@RestController
@RequestMapping(value = "/ciudad", produces = MediaType.APPLICATION_JSON_VALUE)
public class CiudadControllers {
	
	@Autowired
	private CiudadService  ciudadService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	public ResponseEntity<CiudadDTO> create(@RequestBody CiudadDTO ciudadDTO){
		
		// convert DTO to entity
		Ciudad ciudadRequest = modelMapper.map(ciudadDTO, Ciudad.class);
		
		Ciudad ciudad = ciudadService.save(ciudadRequest);
		
		// convert entity to DTO
		CiudadDTO ciudadResponse = modelMapper.map(ciudad, CiudadDTO.class);
		
		
		return new ResponseEntity<CiudadDTO>(ciudadResponse,HttpStatus.CREATED);		
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<CiudadDTO> getCiudadById(@PathVariable(value = "id") Long ciudadId) {
		Optional<Ciudad> ciudad = ciudadService.findById(ciudadId);

		// convert entity to DTO
		
		if (ciudad.isPresent()){
			CiudadDTO ciudadResponse = modelMapper.map(ciudad.get(), CiudadDTO.class);
            return ResponseEntity.ok(ciudadResponse);
        }
		else {
			return ResponseEntity.notFound().build();
        }

	}
	
	// Update an User 
		@PutMapping("/{id}")
		public ResponseEntity<CiudadDTO> update(@RequestBody CiudadDTO ciudadDTO, @PathVariable(value = "id") Long ciudadId) {
			// convert DTO to Entity
			Ciudad ciudadRequest = modelMapper.map(ciudadDTO, Ciudad.class);

			Ciudad ciudad = ciudadService.update(ciudadRequest, ciudadId);

			// entity to DTO
			CiudadDTO ciudadResponse = modelMapper.map(ciudad, CiudadDTO.class);
			if(ciudad==null) {
				return ResponseEntity.notFound().build();
			}
			else {
	            return ResponseEntity.ok(ciudadResponse);}
	      

		}
	
	

}
