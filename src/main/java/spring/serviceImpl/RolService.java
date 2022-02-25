package spring.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.enums.RolNombre;
import spring.modelo.Rol;
import spring.repository.RolRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        return rolRepository.findByRolNombre(rolNombre);
    }

    public void save(Rol rol){
        rolRepository.save(rol);
    }
    
    @Transactional(readOnly = true)
   	public  ArrayList<Rol>  findAll() {
   		return  (ArrayList<Rol>)rolRepository.findAll();
   	}
}
