package spring.interfaceService;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import spring.modelo.CuentaCorriente;

public interface ICuentaCorrienteService {

	public Iterable<CuentaCorriente> findAll();
	
	public Page<CuentaCorriente> findAll(Pageable pageable);
	
	public Optional<CuentaCorriente> findById(Long id);
	
	public CuentaCorriente save(CuentaCorriente user);
	
	public CuentaCorriente update(CuentaCorriente user,Long id);
	
	public void deleteById(Long id);
}
