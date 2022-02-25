package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.modelo.Ciudad;

@Repository
public interface CiudadRepository  extends JpaRepository<Ciudad, Long>{

}
