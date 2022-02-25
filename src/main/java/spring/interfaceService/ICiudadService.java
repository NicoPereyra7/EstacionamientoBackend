package spring.interfaceService;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import spring.modelo.Ciudad;


public interface ICiudadService {
	
	public Iterable<Ciudad> findAll();
	
	public Page<Ciudad> findAll(Pageable pageable);
	
	public Optional<Ciudad> findById(Long id);
	
	public Ciudad save(Ciudad user);
	
	public Ciudad update(Ciudad user,Long id);
	
	public void deleteById(Long id);

}
