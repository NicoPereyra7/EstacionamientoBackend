package spring.serviceImpl;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.interfaceService.IFeriadoService;
import spring.modelo.Feriado;
import spring.repository.FeriadoRepository;

@Service
public class FeriadoService implements IFeriadoService {
	
	@Autowired
	FeriadoRepository feriadoRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Feriado> findAll() {
		return feriadoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Feriado> findAll(Pageable pageable) {
		return feriadoRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Feriado> findById(Long id) {
		return feriadoRepository.findById(id);
	}
	
	@Transactional(readOnly = true)
	public Optional<Feriado> findByFecha(String fecha) {
		return feriadoRepository.findByFecha(fecha);
	}

	@Override
	@Transactional
	public Feriado save(Feriado feriado) {
		return feriadoRepository.save(feriado);
	}

	@Override
	@Transactional
	public Feriado update(Feriado feriadoRequest, Long id) {
		Optional<Feriado> feriado = feriadoRepository.findById(id);
		if (feriado.isPresent()) {
			feriado.get().setFecha(feriadoRequest.getFecha()); 
			return feriadoRepository.save(feriado.get());
		} else
			return null;
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		feriadoRepository.deleteById(id);
	}
	
	public boolean fechaValida(String patente) {
	     Pattern pat = Pattern.compile("[0-9]{4}[/][0-9]{2}[/][0-9]{2}");
	     Matcher mat = pat.matcher(patente);
		if (mat.matches()) {
			return true;                                                                                
	     } else {
	    	 return false;                                                                                  
	     }
	}

}
