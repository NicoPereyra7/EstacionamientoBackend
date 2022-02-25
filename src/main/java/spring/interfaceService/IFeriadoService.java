package spring.interfaceService;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import spring.modelo.Feriado;

public interface IFeriadoService {
	
	public Iterable<Feriado> findAll();
	
	public Page<Feriado> findAll(Pageable pageable);
	
	public Optional<Feriado> findById(Long id);
	
	public Feriado save(Feriado feriado);
	
	public Feriado update(Feriado feriado,Long id);
	
	public void deleteById(Long id);
}
