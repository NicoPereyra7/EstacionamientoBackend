package spring.interfaceService;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import spring.modelo.OperacionesCuenta;

public interface IOperacionesCuenta  {
	
	public Iterable<OperacionesCuenta> findAll();
	
	public Page<OperacionesCuenta> findAll(Pageable pageable);
	
	public Optional<OperacionesCuenta> findById(Long id);
	
	public OperacionesCuenta save(OperacionesCuenta user);
	
	public OperacionesCuenta update(OperacionesCuenta user,Long id);
	
	public void deleteById(Long id);

}
