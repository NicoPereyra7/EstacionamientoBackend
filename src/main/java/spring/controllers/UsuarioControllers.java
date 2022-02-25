package spring.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.dto.CuentaCorrienteDTO;
import spring.dto.JwtDTO;
import spring.dto.LoginDTO;
import spring.dto.UsuarioDTO;
import spring.dto.Mensaje;
import spring.enums.RolNombre;
import spring.jwt.JwtProvider;
import spring.modelo.CuentaCorriente;
import spring.modelo.Rol;
import spring.modelo.Usuario;
import spring.serviceImpl.CuentaCorrienteService;
import spring.serviceImpl.RolService;
import spring.serviceImpl.UsuarioService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioControllers {

	@Autowired
	private UsuarioService userService;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CuentaCorrienteService cuentaCorrienteService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
    @Autowired
	AuthenticationManager authenticationManager;
    
    @Autowired
	RolService rolService;
    
	@Autowired
	JwtProvider jwtProvider;


	
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody UsuarioDTO usuarioDTO, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			List<String> errors = new ArrayList<>();
			errors.add("El o los campos telefono/clave  incorrecta");
			response.put("errors", errors);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);			
		}
		
		if(userService.existsByMail(usuarioDTO.getMail())) {
			return new ResponseEntity<Mensaje>(new Mensaje("El usuario con ese email ya existe"), HttpStatus.BAD_REQUEST);
		}
		if(userService.existsByTelefono(usuarioDTO.getTelefono())) {
			return new ResponseEntity<Mensaje>(new Mensaje("El usuario con ese telefono ya existe"), HttpStatus.BAD_REQUEST);
		}
		
		// convert DTO to entity
		Usuario userRequest = modelMapper.map(usuarioDTO, Usuario.class);
		// if del si ya existe usuario
		Usuario user = new Usuario(userRequest.getMail(),
				userRequest.getTelefono(),passwordEncoder.encode(userRequest.getClave()));		
		
		Set<Rol> roles = new HashSet<>();
		roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
		user.setRoles(roles);
		userService.save(user); 
		
		Optional<Usuario> userId = userService.findById(user.getId());
		
		CuentaCorriente cuenta = new CuentaCorriente(10000, userId.get().getTelefono());
		cuenta.setUsuario(user);
		cuentaCorrienteService.save(cuenta);		
		
		// convert entity to DTO
		CuentaCorrienteDTO cuentaCResponse = modelMapper.map(cuenta, CuentaCorrienteDTO.class);		
		UsuarioDTO userResponse = modelMapper.map(user, UsuarioDTO.class);
		userResponse.setCuentaCorriente(cuentaCResponse);		
		return new ResponseEntity<UsuarioDTO>(userResponse, HttpStatus.CREATED);
	}

	@PostMapping("/autenticacion")
	public ResponseEntity<?> autenticar(@Valid @RequestBody LoginDTO loginDto,BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			return new ResponseEntity<Mensaje>(new Mensaje("El telefono/contraseña es incorrecta"), HttpStatus.BAD_REQUEST);		
		}
		
		Authentication authentication = authenticationManager.
				authenticate(new UsernamePasswordAuthenticationToken(loginDto.getTelefono(), loginDto.getClave()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		JwtDTO jwtDto = new JwtDTO(jwt);
		
		return new ResponseEntity(jwtDto, HttpStatus.OK);

	}



	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDTO> getUserById(@PathVariable(value = "id") Long userId) {
		Optional<Usuario> user = userService.findById(userId);

		// convert entity to DTO
		UsuarioDTO userResponse = modelMapper.map(user.get(), UsuarioDTO.class);
		if (user.isPresent()){
            return ResponseEntity.ok(userResponse);
        }
		else {
			return ResponseEntity.notFound().build();
        }

	}

	// Update an User 
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody UsuarioDTO userDTO, BindingResult result, @PathVariable(value = "id") Long userId) {
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			List<String> errors = new ArrayList<>();			
			for(FieldError err: result.getFieldErrors()) {
				errors.add( "El campo '" + err.getField() + "' " + err.getDefaultMessage());
			}
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);					
		}
		// convert DTO to Entity
		Usuario userRequest = modelMapper.map(userDTO, Usuario.class);
		Usuario user = new Usuario(userRequest.getMail(),
				userRequest.getTelefono(),passwordEncoder.encode(userRequest.getClave()));
		userService.update(userRequest, userId);
		// entity to DTO
		UsuarioDTO userResponse = modelMapper.map(user, UsuarioDTO.class);
		if(user==null) {
			return ResponseEntity.notFound().build();
		}
		else {
            return ResponseEntity.ok(userResponse);} 
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<JwtDTO> refresh(@RequestBody JwtDTO jwtDto) throws ParseException {
		String token = jwtProvider.refreshToken(jwtDto);
		JwtDTO jwt = new JwtDTO(token);
		return new ResponseEntity<JwtDTO>(jwt, HttpStatus.OK);
	}
	

}
