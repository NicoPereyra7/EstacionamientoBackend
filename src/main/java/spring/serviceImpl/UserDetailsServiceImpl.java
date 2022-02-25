package spring.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import spring.modelo.Usuario;
import spring.modelo.UsuarioPrincipal;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String telefono) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.getByTelefono(telefono).get();
        return UsuarioPrincipal.build(usuario);
    }
}
