package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import spring.modelo.OperacionesCuenta;

@Repository
public interface OperacionesCuentaRepository  extends  JpaRepository<OperacionesCuenta , Long> {
	
	@Query(value = "SELECT * FROM operaciones_cuenta o WHERE o.cuenta_id = ?1", nativeQuery = true)
	Iterable<OperacionesCuenta> existAllOperacionesCuentaByCuenta(Long id);
	

}
