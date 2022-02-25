package spring.interfaceService;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import spring.modelo.Patente;


public interface IPatenteService {

	public Iterable<Patente> findAll();
	
	public Page<Patente> findAll(Pageable pageable);
	
	public Optional<Patente> findById(Long id);
	
	public Patente save(Patente user);
	
	public Patente update(Patente user,Long id);
	
	public void deleteById(Long id);
}
