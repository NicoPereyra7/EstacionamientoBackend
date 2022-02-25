package spring.serviceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.interfaceService.IEstacionamiento;
import spring.modelo.Ciudad;
import spring.modelo.Estacionamiento;
import spring.repository.EstacionamientoRepository;

@Service
public class EstacionamientoService implements IEstacionamiento {
	
	@Autowired()
	EstacionamientoRepository estacionRepo;
	@Autowired
	CiudadService ciudadService;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Estacionamiento> findAll() {
		return estacionRepo.findAll();
	}
	

	@Override
	@Transactional(readOnly = true)
	public Page<Estacionamiento> findAll(Pageable pageable) {
		return estacionRepo.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Estacionamiento> findById(Long id) {
		return estacionRepo.findById(id);
	}

	@Override
	@Transactional
	public Estacionamiento save(Estacionamiento estacionamiento) {
		return estacionRepo.save(estacionamiento);
	}
	
	@Override
	@Transactional
	public void deleteById(Long id) {
		estacionRepo.deleteById(id);
	}
	
	@Override
	@Transactional
	public Estacionamiento update(Estacionamiento estacionamientoRequest, Long id) {
		return null;
	}
	
	
   	@Transactional
	public Estacionamiento finalizar(Estacionamiento estacionamientoRequest) {
			Optional<Ciudad> ciudad = ciudadService.findById(Long.valueOf(1));
			
			//estacionamiento.get().setTotal((locaDate.getHour()-estacionamiento.get().getHoraInicio())*ciudad.get().ge);
			
			
			return estacionamientoRequest;
			
		
	}
	
	
 
	@Transactional(readOnly = true)
	public Optional<Estacionamiento> findbyPatenteActiva(Long id) {
		return estacionRepo.findbyPatenteActiva(id);
	}
	
	@Transactional(readOnly = true)
	public Optional<Estacionamiento> findByActivoPorUsuario(Long id) {
		return estacionRepo.findByActivoPorUsuario(id);		
	}
	
	@Transactional(readOnly = true)
	public Iterable<Estacionamiento> findbyEstacionamientosActivos(Long id) {
		return estacionRepo.findbyEstacionamientosActivos(id);
	}	
	

}
