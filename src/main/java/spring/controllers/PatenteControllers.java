package spring.controllers;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import spring.dto.Mensaje;
import spring.dto.PatenteDTO;
import spring.modelo.Patente;
import spring.modelo.Usuario;
import spring.serviceImpl.PatenteService;
import spring.serviceImpl.UsuarioService;

@RestController
@RequestMapping(value = "/patente", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200")
public class PatenteControllers {
	@Autowired
	private PatenteService  patenteService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody PatenteDTO patenteDTO, BindingResult result){
				
		// convertir DTO a entity
		Patente patenteRequest = modelMapper.map(patenteDTO, Patente.class);
		
		// buscar user
		Optional<Usuario> idUser=usuarioService.findById(patenteDTO.getUsuario_id());
		
		if(!idUser.isPresent() ) {
			return new ResponseEntity<Mensaje>(new Mensaje("El usuario no existe "), HttpStatus.BAD_REQUEST);
		}
		
		// Verifico si la patente sigue el formato establecido
		if(!patenteService.patenteValida(patenteRequest.getPatente())) {
			return new ResponseEntity<Mensaje>(new Mensaje("Patente ("+patenteRequest.getPatente()+") invalida debe seguir el formato AAA123 o AB123CD "), HttpStatus.BAD_REQUEST);
		}
		//Paso el string de la patente y me devuelve el mismo String todo en MAYUSCULA 
		patenteRequest.setPatente(patenteService.cadenaEnMayuscula(patenteRequest.getPatente()));
		//Guardar usuario en patente
		patenteRequest.setUsuario(idUser.get());
		// verifico que no exista la patente repetida para un usuario
		if (patenteService.existsByPatenteAndUsuario(patenteDTO.getPatente(), patenteDTO.getUsuario_id()) != null) {
			return new ResponseEntity<Mensaje>(
					new Mensaje("la patente " + patenteDTO.getPatente() + " ya existe en su listado de patentes en el Usuario"),
					HttpStatus.BAD_REQUEST);
		}
		// Marco la patente como activa
		patenteRequest.setActivo(true);;
		//Guardar patente en el sistema
		Patente patente = patenteService.save(patenteRequest);
		// convert entity to DTO
		PatenteDTO patenteResponse = modelMapper.map(patente, PatenteDTO.class);		
		return new ResponseEntity<PatenteDTO>(patenteResponse,HttpStatus.CREATED);		
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<PatenteDTO> getPatenteById(@PathVariable(value = "id") Long patenteId) {
		Optional<Patente> patente = patenteService.findById(patenteId);

		// convert entity to DTO
		PatenteDTO patenteResponse = modelMapper.map(patente.get(), PatenteDTO.class);
		if (patente.isPresent()){
            return ResponseEntity.ok(patenteResponse);
        }
		else {
			return ResponseEntity.notFound().build();
        }

	}
	
	
		@PutMapping("/{id}")
		public ResponseEntity<PatenteDTO> update(@RequestBody PatenteDTO patenteDTO, @PathVariable(value = "id") Long patenteId) {
			// convert DTO to Entity
			Patente patenteRequest = modelMapper.map(patenteDTO, Patente.class);

			Patente patente = patenteService.update(patenteRequest, patenteId);

			// entity to DTO
			PatenteDTO patenteResponse = modelMapper.map(patente, PatenteDTO.class);
			if(patente==null) {
				return ResponseEntity.notFound().build();
			}
			else {
	            return ResponseEntity.ok(patenteResponse);}
	      

		}
		@DeleteMapping("/{id}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void delete(@PathVariable(value = "id") Long patenteId) {
			patenteService.deleteById(patenteId);


		}
		
		@GetMapping("/usuario/{id}")
		public ResponseEntity<Iterable<Patente>> getPatentesByUser(@PathVariable(value = "id") Long userId) {
			return ResponseEntity.ok(patenteService.findAllByUsuario(userId));

		}
}
