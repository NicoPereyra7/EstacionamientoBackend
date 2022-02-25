package spring.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import spring.dto.CuentaCorrienteDTO;
import spring.dto.Mensaje;
import spring.modelo.CuentaCorriente;
import spring.modelo.OperacionesCuenta;
import spring.serviceImpl.CuentaCorrienteService;
import spring.serviceImpl.OperacionesCuentaService;

@RestController
@RequestMapping(value = "/cuentaCorriente", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200")
public class CuentaCorrienteControllers {
	
	@Autowired
	private CuentaCorrienteService  cuentaCorrienteService;
	
	@Autowired
	private OperacionesCuentaService operacionesCuentaService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	public ResponseEntity<CuentaCorrienteDTO> create(@RequestBody CuentaCorrienteDTO cuentaCorrienteDTO){
		
		// convert DTO to entity
		CuentaCorriente cuentaCorrienteRequest = modelMapper.map(cuentaCorrienteDTO, CuentaCorriente.class);
		
		CuentaCorriente cuentaCorriente = cuentaCorrienteService.save(cuentaCorrienteRequest);
		
		// convert entity to DTO
		CuentaCorrienteDTO cuentaCorrienteResponse = modelMapper.map(cuentaCorriente, CuentaCorrienteDTO.class);
		
		
		return new ResponseEntity<CuentaCorrienteDTO>(cuentaCorrienteResponse,HttpStatus.CREATED);		
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<CuentaCorrienteDTO> getCuentaCorrienteById(@PathVariable(value = "id") Long cuentaCorrienteId) {
		Optional<CuentaCorriente> cuentaCorriente = cuentaCorrienteService.findByUsuario(cuentaCorrienteId);

		// convert entity to DTO
		CuentaCorrienteDTO cuentaCorrienteResponse = modelMapper.map(cuentaCorriente.get(), CuentaCorrienteDTO.class);
		if (cuentaCorriente.isPresent()){
            return ResponseEntity.ok(cuentaCorrienteResponse);
        }
		else {
			return ResponseEntity.notFound().build();
        }

	}
	
	@GetMapping("/usuario/{id}")
	public ResponseEntity<CuentaCorrienteDTO> getCuentaCorrienteByUsuario(@PathVariable(value = "id") Long UsuarioId) {
		Optional<CuentaCorriente> cuentaCorriente = cuentaCorrienteService.findByUsuario(UsuarioId);

		// convert entity to DTO
		CuentaCorrienteDTO cuentaCorrienteResponse = modelMapper.map(cuentaCorriente.get(), CuentaCorrienteDTO.class);
		if (cuentaCorriente.isPresent()){
            return ResponseEntity.ok(cuentaCorrienteResponse);
        }
		else {
			return ResponseEntity.notFound().build();
        }

	}
	
	
		@PutMapping("/cargarSaldo")
		public ResponseEntity<?> actualizarCuenta(@RequestBody CuentaCorrienteDTO cuentaCorrienteDTO) {
			// convert DTO to Entity
			Optional<CuentaCorriente> cuentaCorrienteRequest = cuentaCorrienteService.findByTelefono(cuentaCorrienteDTO.getTelefono());			
			if(!cuentaCorrienteRequest.isPresent()) {
				return new ResponseEntity<Mensaje>(new Mensaje("No se encuentra la cuenta corriente "), HttpStatus.BAD_REQUEST);
			}
			if(cuentaCorrienteDTO.getSaldo()<100) {
				return new ResponseEntity<Mensaje>(new Mensaje("El Monto a cargar DEBE ser mayor a $100 "), HttpStatus.BAD_REQUEST);				
			}
			
			cuentaCorrienteRequest.get().setSaldo(cuentaCorrienteRequest.get().getSaldo() + cuentaCorrienteDTO.getSaldo());
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");			
			String fecha = dtf.format(LocalDateTime.now());
			
			OperacionesCuenta  operacion = new OperacionesCuenta("carga", fecha,cuentaCorrienteDTO.getSaldo() , cuentaCorrienteRequest.get().getSaldo(), cuentaCorrienteRequest.get());
			operacionesCuentaService.save(operacion);
			
			CuentaCorriente cuentaCorriente = cuentaCorrienteService.update(cuentaCorrienteRequest.get(), cuentaCorrienteRequest.get().getId());

			// entity to DTO
			CuentaCorrienteDTO cuentaCorrienteResponse = modelMapper.map(cuentaCorriente, CuentaCorrienteDTO.class);
			if(cuentaCorriente==null) {
				return ResponseEntity.notFound().build();
			}
			else {
	            return ResponseEntity.ok(cuentaCorrienteResponse);}     

		}

}
