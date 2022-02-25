package spring.controllers;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.dto.EstacionamientoDTO;
import spring.dto.Mensaje;
import spring.modelo.Ciudad;
import spring.modelo.CuentaCorriente;
import spring.modelo.Estacionamiento;
import spring.modelo.Feriado;
import spring.modelo.Fraccionador;
import spring.modelo.OperacionesCuenta;
import spring.modelo.Patente;
import spring.modelo.Usuario;
import spring.serviceImpl.CiudadService;
import spring.serviceImpl.CuentaCorrienteService;
import spring.serviceImpl.EstacionamientoService;
import spring.serviceImpl.FeriadoService;
import spring.serviceImpl.FraccionadorService;
import spring.serviceImpl.OperacionesCuentaService;
import spring.serviceImpl.PatenteService;
import spring.serviceImpl.UsuarioService;


@RestController
@RequestMapping(value = "/estacionamiento", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200")
public class EstacionamientoControllers {	
		
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private EstacionamientoService estacionamientoService;
	
	@Autowired
	private CuentaCorrienteService cuentaCorrienteService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PatenteService patenteService;
	
	@Autowired
	private CiudadService ciudadService;
	
	@Autowired
	private OperacionesCuentaService operacionesCuentaService;
	
	@Autowired
	private FraccionadorService fraccionadorService;	
	
	@Autowired
	private FeriadoService feriadoService;
	
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody EstacionamientoDTO estacionamientoDTO) throws ParseException{
			
		// convert DTO to entity
		Estacionamiento estacionamientoRequest = modelMapper.map(estacionamientoDTO, Estacionamiento.class);
		
		//Verifico si Existe Usuario por id
		Optional<Usuario> idUsuario=usuarioService.findById(estacionamientoDTO.getUsuario_id());
		if(!idUsuario.isPresent() ) {
			return new ResponseEntity<Mensaje>(new Mensaje("No se encuentra el Usuario "), HttpStatus.BAD_REQUEST);
		}
				
		// Verifico si la patente sigue el formato establecido
		if(!patenteService.patenteValida(estacionamientoDTO.getPatente())) {
			return new ResponseEntity<Mensaje>(new Mensaje("Patente ("+ estacionamientoDTO.getPatente()+ ") invalida debe seguir el formato AAA123 o AB123CD "), HttpStatus.BAD_REQUEST);
		}
		//Paso el string de la patente y me devuelve el mismo String todo en MAYUSCULA 
		Patente patente= new Patente();
		patente.setPatente(patenteService.cadenaEnMayuscula(estacionamientoDTO.getPatente()));
		
		//setteo el usuario
		patente.setUsuario(idUsuario.get());
		
		if (patenteService.existsByPatenteAndUsuario(patente.getPatente(), patente.getUsuario().getId()) == null) {
			//Guardar patente en el sistema
			patente.setActivo(false);
			Patente patenteNueva = patenteService.save(patente);
			patente.setId(patenteNueva.getId());
		} else {
			Patente idPatente = patenteService.existsByPatenteAndUsuario(patente.getPatente(), patente.getUsuario().getId());
			patente.setId(idPatente.getId());			
		}
		
		// Verifico que la patente no este Activa en el sistema para cualquier usuario
		Optional<Estacionamiento> estacionamientoActivo = estacionamientoService.findbyPatenteActiva(patente.getId());
		if(estacionamientoActivo.isPresent()) {
			return new ResponseEntity<Mensaje>(new Mensaje("Patente Activa en el sistema"), HttpStatus.BAD_REQUEST);
		}
		
		//Verifico si Existe una Cuenta Corriente asociada al Usuario por telefono
		Optional<CuentaCorriente> cuentaCorriente=cuentaCorrienteService.findByTelefono(idUsuario.get().getTelefono());
		if(!cuentaCorriente.isPresent()) {
			return new ResponseEntity<Mensaje>(new Mensaje("No se encuentra una cuenta corriente asociada al telefono del usuario en el sistema "), HttpStatus.BAD_REQUEST);
		}
		
		//Verifico si Existe una ciudad en el sistema
		Optional<Ciudad> ciudad = ciudadService.findById(Long.valueOf(1)); // En este caso 1 sola ciudad parametro estatico en 1 teniendo en cuenta que en la base se crea con ese id
		if(!ciudad.isPresent()) {
			 return new ResponseEntity<Mensaje>(new Mensaje("No se encuentra La ciudad a la que pertenece el estacionamiento "), HttpStatus.BAD_REQUEST);  
		}		
		
		// Verifico que el usuario no tenga una patente Activa en el sistema
		Optional<Estacionamiento> estacionamientoActivoPorUsuario = estacionamientoService.findByActivoPorUsuario(estacionamientoDTO.getUsuario_id());
		if(estacionamientoActivoPorUsuario.isPresent()) {
			return new ResponseEntity<Mensaje>(new Mensaje("El usuario posee una patente Activa"), HttpStatus.BAD_REQUEST);
		}
		
		//verifico que la cuenta corriente tenga saldo para pagar el minimo de la ciudad
		if(cuentaCorriente.get().getSaldo()<ciudad.get().getPrecio()) {
			return new ResponseEntity<Mensaje>(new Mensaje("El usuario posee dinero suficiente para alcanzar el minimo de una hora"), HttpStatus.BAD_REQUEST);
		}
		
		//Guardo la patente en el Estacionamiento
		estacionamientoRequest.setPatente(patente);		
		
		//Guardo el Usuario en el Estacionamiento
		estacionamientoRequest.setUsuario(idUsuario.get());		
		
		//Asigno y formateo el dia y la hora de inicio 
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		estacionamientoRequest.setHoraInicio(dtf.format(LocalDateTime.now()));
		
		String fecha = estacionamientoRequest.getHoraInicio().split(" ")[0];
		Date d1 = new Date(fecha);		
		int dia = d1.getDay();
		//verificar el formato de la fech
		if ((dia == 0) || dia == 6 ) {  //or sunday   
			return new ResponseEntity<Mensaje>(new Mensaje("El dia es fin de semana "), HttpStatus.BAD_REQUEST);
	    }
		
		Optional<Feriado>  existe= feriadoService.findByFecha(fecha);
		if(existe.isPresent() ) {
			return new ResponseEntity<Mensaje>(new Mensaje("Esa fecha se encuentra en la lista de feriados"), HttpStatus.BAD_REQUEST);
		}
		// verifico que se encuentre dentro del tiempo habilitado en la ciudad
		DateFormat dateFormat = new SimpleDateFormat ("hh:mm:ss");	
		//String today = date.split(" ")[0];
		String hora = estacionamientoRequest.getHoraInicio().split(" ")[1];		
		/**Date date1, date2, dateNueva;
		date1 = dateFormat.parse(ciudad.get().getHorarioInicio());
		date2 = dateFormat.parse(ciudad.get().getHorarioFin());
		dateNueva = dateFormat.parse(hora);
				
		if ((date1.compareTo(dateNueva) > 0) && (date2.compareTo(dateNueva) < 0)){
			return new ResponseEntity<Mensaje>(new Mensaje("Fuera del horario de la ciudad"), HttpStatus.BAD_REQUEST);
		}**/
		//Asigno el Estado del Estacionamiento como Activo
		estacionamientoRequest.setActivo(true);		
		
		//Guardo Estacionamiento en el sistema
		estacionamientoService.save(estacionamientoRequest);
		
		// convert entity to DTO
		EstacionamientoDTO estacionamientoResponse = modelMapper.map(estacionamientoRequest, EstacionamientoDTO.class);
		return new ResponseEntity<EstacionamientoDTO>(estacionamientoResponse, HttpStatus.CREATED);		
		}
	
	 @GetMapping( path = "/{id}")
	 public ResponseEntity<Optional<Estacionamiento>> obtenerEstacionamientoPorId(@PathVariable("id") Long id) {	    	
	     Optional<Estacionamiento> est = estacionamientoService.findById(id);
	     return new ResponseEntity<Optional<Estacionamiento>>(est, HttpStatus.OK);
	    }
	 
	 @GetMapping( path = "/obtenerActivos/{id}")
	 public ResponseEntity<Iterable<Estacionamiento>> obtenerEstacionamientoActivosPorId(@PathVariable("id") Long id) {
		 return ResponseEntity.ok(estacionamientoService.findbyEstacionamientosActivos(id));
	  }
	 
	 
	@SuppressWarnings("deprecation")
	@GetMapping( path = "/finalizar/{id}")
	 public ResponseEntity<?> finalizarEstacionamiento(@PathVariable("id") Long id) {
		 Optional<Estacionamiento> est = estacionamientoService.findById(id);
		 if (!est.isPresent()) {
			 return new ResponseEntity<Mensaje>(new Mensaje("No se encuentra el Estacionamiento "), HttpStatus.BAD_REQUEST);    		
	    }     	
	    //Asigno y formateo el dia y la hora de fin 
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");			
		est.get().setHoraFin(dtf.format(LocalDateTime.now()));
		
		//Asigno el Estado del Estacionamiento como Activo
		est.get().setActivo(false);
		
		//Calculo del total del costo del estacionamiento
		Optional<Ciudad> ciudad = ciudadService.findById(Long.valueOf(1));
		if(!ciudad.isPresent()) {
			 return new ResponseEntity<Mensaje>(new Mensaje("No se encuentra La ciudad a la que pertenece el estacionamiento "), HttpStatus.BAD_REQUEST);  
		}
		Optional<Fraccionador> fracc = fraccionadorService.findById(Long.valueOf(1));
		if(!fracc.isPresent()) {
			 return new ResponseEntity<Mensaje>(new Mensaje("No se encuentra un Fraccionador de tiempo"), HttpStatus.BAD_REQUEST);  
		}
		long fraccMinutos = fracc.get().getMinutos();
		long partesHora = 60 / fraccMinutos;
		System.out.print(partesHora);
		Date d1= null;
		Date d2= null;		
		SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		try {
			d1 = format.parse(est.get().getHoraInicio()); 
			d2 = format.parse(est.get().getHoraFin());
		}
		catch (ParseException e) {
			e.printStackTrace();
		}		
		long diff = d2.getTime() - d1.getTime();		
		//long diffSeconds = diff / 1000;s
		long diffMinutes = diff / (60 * 1000);
		long partes = (diffMinutes / fraccMinutos) + 1;
		double division = (double)partes/partesHora;
		Double total = ciudad.get().getPrecio() * division;
		
		est.get().setTotal((double)Math.round(total * 100d) / 100d);
		
		Optional<CuentaCorriente> cuentaCorriente=cuentaCorrienteService.findByTelefono(est.get().getUsuario().getTelefono());
		if(!cuentaCorriente.isPresent()) {
			return new ResponseEntity<Mensaje>(new Mensaje("No se encuentra una cuenta corriente asociada al telefono del usuario en el sistema "), HttpStatus.BAD_REQUEST);
		}
		// Creo y guardo la Operacion de la Cuenta
		OperacionesCuenta  operacion = new OperacionesCuenta("debito", est.get().getHoraFin(), est.get().getTotal(), cuentaCorriente.get().getSaldo(), cuentaCorriente.get());
		operacionesCuentaService.save(operacion);
		
		cuentaCorriente.get().setSaldo(cuentaCorriente.get().getSaldo()-est.get().getTotal());
		cuentaCorrienteService.save(cuentaCorriente.get());
		
		estacionamientoService.save(est.get());			
	    return new ResponseEntity<Estacionamiento>(est.get(), HttpStatus.OK);
	    
	   }

}
