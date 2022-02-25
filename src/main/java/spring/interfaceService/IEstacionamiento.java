package spring.interfaceService;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import spring.modelo.Estacionamiento;

public interface IEstacionamiento {
public Iterable<Estacionamiento> findAll();
	
	public Page<Estacionamiento> findAll(Pageable pageable);
	
	public Optional<Estacionamiento> findById(Long id);
	
	public Estacionamiento save(Estacionamiento user);
	
	public Estacionamiento update(Estacionamiento user,Long id);
	
	public void deleteById(Long id);

}
