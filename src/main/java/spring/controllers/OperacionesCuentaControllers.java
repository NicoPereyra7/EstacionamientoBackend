package spring.controllers;

import java.util.List;
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

import spring.dto.OperacionesCuentaDTO;
import spring.modelo.Estacionamiento;
import spring.modelo.OperacionesCuenta;
import spring.serviceImpl.OperacionesCuentaService;

@RestController
@RequestMapping(value = "/operacionesCuenta", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200")
public class OperacionesCuentaControllers {
	@Autowired
	OperacionesCuentaService operacionesCuentaService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	public ResponseEntity<OperacionesCuentaDTO> create(@RequestBody OperacionesCuentaDTO operacionesCuentaDTO){
		
		// convert DTO to entity
		OperacionesCuenta operacionesCuentaRequest = modelMapper.map(operacionesCuentaDTO, OperacionesCuenta.class);
		
		OperacionesCuenta operacionCuenta = operacionesCuentaService.save(operacionesCuentaRequest);
		
		// convert entity to DTO
		OperacionesCuentaDTO operacionCuentaResponse = modelMapper.map(operacionCuenta, OperacionesCuentaDTO.class);
		
		
		return new ResponseEntity<OperacionesCuentaDTO>(operacionCuentaResponse,HttpStatus.CREATED);		
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<OperacionesCuentaDTO> getOperacionCuentaById(@PathVariable(value = "id") Long operacionesCuentaId) {
		Optional<OperacionesCuenta> operacionCuenta = operacionesCuentaService.findById(operacionesCuentaId);	
		
		if (operacionCuenta.isPresent()){
			OperacionesCuentaDTO cuentaCorrienteResponse = modelMapper.map(operacionCuenta.get(), OperacionesCuentaDTO.class);
            return ResponseEntity.ok(cuentaCorrienteResponse);
        }
		else {
			return ResponseEntity.notFound().build();
        }

	}	
	
	
		@PutMapping("/{id}")
		public ResponseEntity<OperacionesCuentaDTO> update(@RequestBody OperacionesCuentaDTO OperacionesCuentaDTO, @PathVariable(value = "id") Long operacionesCuentaId) {
			// convert DTO to Entity
			OperacionesCuenta operacionCuentaRequest = modelMapper.map(OperacionesCuentaDTO, OperacionesCuenta.class);

			OperacionesCuenta operacionCuenta = operacionesCuentaService.update(operacionCuentaRequest, operacionesCuentaId);

			// entity to DTO
			OperacionesCuentaDTO cuentaCorrienteResponse = modelMapper.map(operacionCuenta, OperacionesCuentaDTO.class);
			if(operacionCuenta==null) {				
				return ResponseEntity.notFound().build();
			}
			else {
	            return ResponseEntity.ok(cuentaCorrienteResponse);}
	      

		}
		
		@GetMapping("/cuentaCorriente/{id}")
		public ResponseEntity<Iterable<OperacionesCuenta>> getOperacionesCuentaByCuenta(@PathVariable(value = "id") Long userId) {
			return ResponseEntity.ok(operacionesCuentaService.findAllByCuenta(userId));
		}

}
