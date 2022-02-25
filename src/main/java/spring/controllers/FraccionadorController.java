package spring.controllers;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.dto.FraccionadorDTO;
import spring.dto.Mensaje;
import spring.modelo.Fraccionador;
import spring.serviceImpl.FraccionadorService;

@RestController
@RequestMapping(value = "/fraccionador", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200")
public class FraccionadorController {
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private FraccionadorService fraccionadorService;
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody FraccionadorDTO fraccionadorDTO){
		Fraccionador fraccionadorRequest = modelMapper.map(fraccionadorDTO, Fraccionador.class);
		// verifico que los minutos sean 60 o menos
		if (fraccionadorRequest.getMinutos() >= 60 ) {
			return new ResponseEntity<Mensaje>(new Mensaje("Minutos incorrectos. Deben ser menor o igual a 60 minutos"), HttpStatus.BAD_REQUEST);
		}
		// verifico que los minutos no sean 0 o negativos
		if (fraccionadorRequest.getMinutos() <= 0) {
			return new ResponseEntity<Mensaje>(new Mensaje("Minutos incorrectos. Deben ser mayores a 0"), HttpStatus.BAD_REQUEST);
		}
		// verifico que los minutos sean 60 divisibles entre 60
		if ( (60 % fraccionadorRequest.getMinutos()) != 0 ) {
			return new ResponseEntity<Mensaje>(new Mensaje("Minutos incorrectos. Deben ser un divisor exacto de 60"), HttpStatus.BAD_REQUEST);
		}
		
		
		Fraccionador fraccionador = fraccionadorService.save(fraccionadorRequest);
		
		// convert entity to DTO
		FraccionadorDTO fraccionadorResponse = modelMapper.map(fraccionador, FraccionadorDTO.class);
		
		
		return new ResponseEntity<FraccionadorDTO>(fraccionadorResponse,HttpStatus.CREATED);		
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<FraccionadorDTO> getCiudadById(@PathVariable(value = "id") Long fraccionadorID) {
		Optional<Fraccionador> fraccionador = fraccionadorService.findById(fraccionadorID);

		// convert entity to DTO
		
		if (fraccionador.isPresent()){
			FraccionadorDTO fraccionadorResponse = modelMapper.map(fraccionador.get(), FraccionadorDTO.class);
            return ResponseEntity.ok(fraccionadorResponse);
        }
		else {
			return ResponseEntity.notFound().build();
        }

	}
	
		@PutMapping("/{id}")
		public ResponseEntity<?> update(@RequestBody FraccionadorDTO fraccionadorDTO, @PathVariable(value = "id") Long fraccionadorID) {
			// convert DTO to Entity
			Fraccionador fraccionadorRequest = modelMapper.map(fraccionadorDTO, Fraccionador.class);
			
			// verifico que los minutos sean 60 o menos
			if (fraccionadorRequest.getMinutos() >= 60 ) {
				return new ResponseEntity<Mensaje>(new Mensaje("Minutos incorrectos. Deben ser menor o igual a 60 minutos"), HttpStatus.BAD_REQUEST);
			}
			// verifico que los minutos no sean 0 o negativos
			if (fraccionadorRequest.getMinutos() <= 0) {
				return new ResponseEntity<Mensaje>(new Mensaje("Minutos incorrectos. Deben ser mayores a 0"), HttpStatus.BAD_REQUEST);
			}
			// verifico que los minutos sean 60 divisibles entre 60
			if ( (60 % fraccionadorRequest.getMinutos()) != 0 ) {
				return new ResponseEntity<Mensaje>(new Mensaje("Minutos incorrectos. Deben ser un divisor exacto de 60"), HttpStatus.BAD_REQUEST);
			}

			Fraccionador fraccionador = fraccionadorService.update(fraccionadorRequest, fraccionadorID);

			// entity to DTO
			FraccionadorDTO ciudadResponse = modelMapper.map(fraccionador, FraccionadorDTO.class);
			if(fraccionador==null) {
				return ResponseEntity.notFound().build();
			}
			else {
	            return ResponseEntity.ok(ciudadResponse);}
	      

		}
	

}
