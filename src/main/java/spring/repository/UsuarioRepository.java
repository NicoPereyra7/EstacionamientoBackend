package spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import spring.modelo.Usuario;



@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
	@Query(value="select u from Usuario u where u.telefono = ?1 and u.clave = ?2")
	List<Usuario> findByTelefonoAndClave(String telefono, String pass);
	
	Optional<Usuario> findByTelefono(String telefono);
    boolean existsByTelefono(String telefono);
    boolean existsByTelefonoAndClave(String telefono,String clave);
    boolean existsByMail(String mail);

}
