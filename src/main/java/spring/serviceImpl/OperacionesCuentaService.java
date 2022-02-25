package spring.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.interfaceService.IOperacionesCuenta;
import spring.modelo.OperacionesCuenta;
import spring.modelo.Patente;
import spring.repository.OperacionesCuentaRepository;

@Service
public class OperacionesCuentaService implements IOperacionesCuenta  {
	
	@Autowired
	OperacionesCuentaRepository operacionesCuentaRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<OperacionesCuenta> findAll() {
		return operacionesCuentaRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<OperacionesCuenta> findAll(Pageable pageable) {
		return operacionesCuentaRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<OperacionesCuenta> findById(Long id) {
		return operacionesCuentaRepository.findById(id);	
	}


	@Override
	@Transactional
	public OperacionesCuenta save(OperacionesCuenta cuenta) {
		return operacionesCuentaRepository.save(cuenta);
	}
	
	@Override
	@Transactional
	public void deleteById(Long id) {
		operacionesCuentaRepository.deleteById(id);
	}
	
	@Override
	@Transactional
	public OperacionesCuenta update(OperacionesCuenta operacionCuentaRequest, Long id) {
		Optional<OperacionesCuenta> operacionesCuenta = operacionesCuentaRepository.findById(id);
		if (operacionesCuenta.isPresent()) {
			operacionesCuenta.get().setTipoMovimiento(operacionCuentaRequest.getTipoMovimiento());
			operacionesCuenta.get().setMonto(operacionCuentaRequest.getMonto());
			operacionesCuenta.get().setFecha(operacionCuentaRequest.getFecha());
			operacionesCuenta.get().setSaldoPrevio(operacionCuentaRequest.getSaldoPrevio());			
			return operacionesCuentaRepository.save(operacionesCuenta.get());
		} else
			return null;
	}
	
	
	@Transactional(readOnly = true)
	public Iterable<OperacionesCuenta> findAllByCuenta(Long id) {
		return operacionesCuentaRepository.existAllOperacionesCuentaByCuenta(id);
	}
	
	

}
