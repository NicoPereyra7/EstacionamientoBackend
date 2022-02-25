package spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import spring.modelo.Estacionamiento;

@Repository
public interface EstacionamientoRepository  extends JpaRepository<Estacionamiento , Long> {
	@Query(value= "SELECT * FROM Estacionamiento e WHERE e.patente_id =?1 and e.activo=true", nativeQuery = true)
	Optional<Estacionamiento> findbyPatenteActiva(Long id);
	
	@Query(value = "SELECT * FROM Estacionamiento e WHERE e.activo = 1 and e.usuario_id=?1", nativeQuery = true)
	 Optional<Estacionamiento> findByActivoPorUsuario(Long id);

	@Query(value= "SELECT * FROM Estacionamiento e WHERE e.usuario_id =?1 and e.activo=true", nativeQuery = true)
	Iterable<Estacionamiento> findbyEstacionamientosActivos(Long id);
}
