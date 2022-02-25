package spring.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.interfaceService.ICuentaCorrienteService;
import spring.modelo.CuentaCorriente;
import spring.repository.CuentaCorrienteRepository;

@Service
public class CuentaCorrienteService implements ICuentaCorrienteService{

	@Autowired
	CuentaCorrienteRepository cuentaCorrienteRepo;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<CuentaCorriente> findAll() {
		return cuentaCorrienteRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<CuentaCorriente> findAll(Pageable pageable) {
		return cuentaCorrienteRepo.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<CuentaCorriente> findById(Long id) {
		return cuentaCorrienteRepo.findById(id);
	}
	
	
	@Transactional(readOnly = true)
	public Optional<CuentaCorriente> findByTelefono(String telefono) {
		return cuentaCorrienteRepo.findByTelefono(telefono);
	}
	
	@Transactional(readOnly = true)
	public Optional<CuentaCorriente> findByUsuario(Long id) {
		return cuentaCorrienteRepo.findByUsuario_id(id);
	}

	@Override
	@Transactional
	public CuentaCorriente save(CuentaCorriente user) {
		return cuentaCorrienteRepo.save(user);
	}
	
	@Override
	@Transactional
	public void deleteById(Long id) {
		cuentaCorrienteRepo.deleteById(id);
	}
	
	@Override
	@Transactional
	public CuentaCorriente update(CuentaCorriente cuentaRequest, Long id) {
		Optional<CuentaCorriente> cuenta = cuentaCorrienteRepo.findById(id);
		if (cuenta.isPresent()) {
			cuenta.get().setSaldo(cuentaRequest.getSaldo());
			return cuentaCorrienteRepo.save(cuenta.get());
		} else
			return null;
	}
	
	
}
