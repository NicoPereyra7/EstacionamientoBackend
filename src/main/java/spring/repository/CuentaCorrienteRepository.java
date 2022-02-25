package spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.modelo.CuentaCorriente;

@Repository
public interface CuentaCorrienteRepository  extends JpaRepository<CuentaCorriente , Long> {
	
	Optional<CuentaCorriente> findByTelefono(String telefono);
	
	Optional<CuentaCorriente> findByUsuario_id(Long usuario_id);
}
