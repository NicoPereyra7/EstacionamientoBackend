package spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import spring.modelo.Patente;

@Repository
public interface PatenteRepository  extends JpaRepository<Patente ,Long> {
	@Query(value="SELECT p FROM Patente p WHERE p.usuario.id =?1")
	List<Patente> findAllPatenteByUsuario(Long id);
	
	@Query(value = "SELECT p FROM Patente p WHERE p.patente=?1 and p.usuario.id =?2")
	Patente existsByPatentAndUser(String patent, Long idUser);
}
