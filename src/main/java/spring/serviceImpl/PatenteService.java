package spring.serviceImpl;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.interfaceService.IPatenteService;
import spring.modelo.Patente;
import spring.repository.PatenteRepository;

@Service
public class PatenteService implements IPatenteService {
 
	@Autowired
	private PatenteRepository patenteRepo;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Patente> findAll() {
		return patenteRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Patente> findAll(Pageable pageable) {
		return patenteRepo.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Patente> findById(Long id) {
		return patenteRepo.findById(id);
	}

	@Override
	@Transactional
	public Patente save(Patente user) {
		return patenteRepo.save(user);
	}

	@Override
	@Transactional
	public Patente update(Patente userRequest, Long id) {
		Optional<Patente> pat = patenteRepo.findById(id);
		if (pat.isPresent()) {
			pat.get().setPatente(userRequest.getPatente());
			return patenteRepo.save(pat.get());
		} else
			return null;
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		patenteRepo.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public Iterable<Patente> findAllByUsuario(Long id) {
		return patenteRepo.findAllPatenteByUsuario(id);
	}
	public boolean patenteValida(String patente) {
	     Pattern pat = Pattern.compile("([a-zA-Z]{3}\\d{3})|([a-zA-Z]{2}\\d{3}[a-zA-Z]{2})");
	     Matcher mat = pat.matcher(patente);
		if (mat.matches()) {
			return true;                                                                                
	     } else {
	    	 return false;                                                                                  
	     }
	}
	
	public String cadenaEnMayuscula(String patente) {		
		return patente.toUpperCase();
	}
	
	@Transactional(readOnly = true)
	public Patente existsByPatenteAndUsuario(String patent, Long idUser) {
		return patenteRepo.existsByPatentAndUser(patent, idUser);
	}	

}
