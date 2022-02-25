package spring.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.interfaceService.IFraccionadorService;
import spring.modelo.Fraccionador;
import spring.repository.FraccionadorRepository;

@Service
public class FraccionadorService implements IFraccionadorService  {
	@Autowired
	FraccionadorRepository fraccionadorRepository;
	
	@Transactional(readOnly = true)
	public Iterable<Fraccionador> findAll() {
		return fraccionadorRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Fraccionador> findById(Long id) {
		return fraccionadorRepository.findById(id);
	}

	@Override
	@Transactional
	public Fraccionador save(Fraccionador fraccionador) {
		return fraccionadorRepository.save(fraccionador);
	}
	
	@Override
	@Transactional
	public Fraccionador update(Fraccionador fraccionador, Long id) {
		Optional<Fraccionador> fracc = fraccionadorRepository.findById(id);
		if (fracc.isPresent()) {
			fracc.get().setMinutos(fraccionador.getMinutos()); 
			return fraccionadorRepository.save(fracc.get());
		} else
			return null;
	}
	


	@Override
	@Transactional
	public void deleteById(Long id) {
		fraccionadorRepository.deleteById(id);
	}
}
