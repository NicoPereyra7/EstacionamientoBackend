package spring.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.interfaceService.ICiudadService;
import spring.modelo.Ciudad;
import spring.repository.CiudadRepository;

@Service
public class CiudadService  implements ICiudadService {
	
	@Autowired
	CiudadRepository ciudadRepo;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Ciudad> findAll() {
		return ciudadRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Ciudad> findAll(Pageable pageable) {
		return ciudadRepo.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Ciudad> findById(Long id) {
		return ciudadRepo.findById(id);
	}

	@Override
	@Transactional
	public Ciudad save(Ciudad ciudad) {
		return ciudadRepo.save(ciudad);
	}

	@Override
	@Transactional
	public Ciudad update(Ciudad userRequest, Long id) {
		Optional<Ciudad> ciudad = ciudadRepo.findById(id);
		if (ciudad.isPresent()) {
			ciudad.get().setHorarioInicio(userRequest.getHorarioInicio());
			ciudad.get().setHorarioFin(userRequest.getHorarioFin());
			ciudad.get().setPrecio(userRequest.getPrecio());
			return ciudadRepo.save(ciudad.get());
		} else
			return null;
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		ciudadRepo.deleteById(id);
	}


}
