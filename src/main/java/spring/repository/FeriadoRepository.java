package spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.modelo.Feriado;

@Repository
public interface FeriadoRepository extends JpaRepository<Feriado , Long> {
	
	Optional<Feriado> findByFecha(String fecha);

}
