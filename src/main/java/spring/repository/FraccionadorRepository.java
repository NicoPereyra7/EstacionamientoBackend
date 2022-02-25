package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.modelo.Fraccionador;

@Repository
public interface FraccionadorRepository  extends JpaRepository<Fraccionador, Long> {

}
