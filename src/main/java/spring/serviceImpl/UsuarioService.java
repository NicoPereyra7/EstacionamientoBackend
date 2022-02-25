package spring.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.interfaceService.IUsuarioService;
import spring.modelo.Usuario;
import spring.repository.UsuarioRepository;

@Service
public class UsuarioService implements IUsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Usuario> findAll() {
		return usuarioRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> findAll(Pageable pageable) {
		return usuarioRepo.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Usuario> findById(Long id) {
		return usuarioRepo.findById(id);
	}

	@Override
	@Transactional
	public Usuario save(Usuario user) {
		return usuarioRepo.save(user);
	}
	
	@Override
	@Transactional
	public void deleteById(Long id) {
		usuarioRepo.deleteById(id);
	}
	
	@Override
	@Transactional
	public Usuario update(Usuario userRequest, Long id) {
		Optional<Usuario> user = usuarioRepo.findById(id);
		if (user.isPresent()) {
			user.get().setTelefono(userRequest.getTelefono());
			user.get().setClave(userRequest.getClave());
			user.get().setMail(userRequest.getMail());
			return usuarioRepo.save(user.get());
		} else
			return null;
	}
	
	@Transactional(readOnly = true)
	public Optional<Usuario> getByTelefono(String telefono) {
		return usuarioRepo.findByTelefono(telefono);
	}
	
	@Transactional(readOnly = true)
	public List<Usuario> findByUserPhoneAndPassword(String telefono, String clave) {
		return usuarioRepo.findByTelefonoAndClave(telefono, clave);
	}

	@Transactional(readOnly = true)
	public boolean existsByTelefono(String telefono) {
		return usuarioRepo.existsByTelefono(telefono);
	}

	@Transactional(readOnly = true)
	public boolean existsByTelefonoAndClave(String telefono,String clave) {
		return usuarioRepo.existsByTelefonoAndClave(telefono, clave);
	}
	
	@Transactional(readOnly = true)
	public  boolean existsByMail(String mail){
		return usuarioRepo.existsByMail(mail);
	}
}
