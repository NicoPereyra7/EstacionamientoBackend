package spring.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.dto.FeriadoDTO;
import spring.dto.Mensaje;
import spring.modelo.Feriado;
import spring.serviceImpl.FeriadoService;

@RestController
@RequestMapping(value = "/feriado", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200")
public class FeriadoControllers {
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private FeriadoService feriadoService;
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody FeriadoDTO feriadoDTO){
		
		// convert DTO to entity
		Feriado feriadoRequest = modelMapper.map(feriadoDTO, Feriado.class);
		//formato  de la fecha verificar
		if (!feriadoService.fechaValida(feriadoRequest.getFecha())) {
			return new ResponseEntity<Mensaje>(new Mensaje("La fecha debe seguis el formato 1999/12/31 "), HttpStatus.BAD_REQUEST);
		}
		//
		//verifico si existe ese feriado
		Optional<Feriado>  existe= feriadoService.findByFecha(feriadoRequest.getFecha());
		if(existe.isPresent() ) {
			return new ResponseEntity<Mensaje>(new Mensaje("Esa fecha ya se encuentra en la lista de feriados "), HttpStatus.BAD_REQUEST);
		}
		//
		Date d1 = new Date(feriadoRequest.getFecha());		
		int dia = d1.getDay();
		//verificar el formato de la fech
		if ((dia == 0) || dia == 6 ) {  //or sunday   
			System.out.println("WEEKEND PRICE");
			return new ResponseEntity<Mensaje>(new Mensaje("El dia es fin de semana "), HttpStatus.BAD_REQUEST);
	    }
		else {
		
	    	Feriado feriado = feriadoService.save(feriadoRequest);
		
	    	// convert entity to DTO
	    	FeriadoDTO feriadoResponse = modelMapper.map(feriado, FeriadoDTO.class);
		
		
	    	return new ResponseEntity<FeriadoDTO>(feriadoResponse,HttpStatus.CREATED);		
	    }
	}
	
	
	@GetMapping
	public ResponseEntity<Iterable<Feriado>> getFeriados() {
		return ResponseEntity.ok(feriadoService.findAll());
	}
}
