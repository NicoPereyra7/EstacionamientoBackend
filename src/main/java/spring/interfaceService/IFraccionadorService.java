package spring.interfaceService;

import java.util.Optional;


import spring.modelo.Fraccionador;

public interface IFraccionadorService {
	
	public Iterable<Fraccionador> findAll();
	
	public Optional<Fraccionador> findById(Long id);
	
	public Fraccionador save(Fraccionador fraccionador);
	
	public Fraccionador update(Fraccionador fraccionador,Long id);
	
	public void deleteById(Long id);

}
