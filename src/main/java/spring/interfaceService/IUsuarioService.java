package spring.interfaceService;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import spring.modelo.Usuario;


public interface IUsuarioService {

	public Iterable<Usuario> findAll();
	
	public Page<Usuario> findAll(Pageable pageable);
	
	public Optional<Usuario> findById(Long id);
	
	public Usuario save(Usuario user);
	
	public Usuario update(Usuario user,Long id);
	
	public void deleteById(Long id);
}
