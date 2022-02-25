package spring.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="estacionamiento")
public class Estacionamiento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	@NotNull
	private String horaInicio;
	@Column
	private String horaFin;
	@Column
	private double total;
	@NotNull
	@Column
	private boolean activo;
	
	@ManyToOne()
	private Usuario usuario;
	@ManyToOne()
	private Patente patente;
	
	public Estacionamiento() {}
	
	public Estacionamiento(Long id,@NotNull String horaInicio, @NotNull boolean activo, Usuario usuario,
			Patente patente) {
		super();
		this.id = id;
		this.horaInicio = horaInicio;
		this.activo = activo;
		this.usuario = usuario;
		this.patente = patente;
	}	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Patente getPatente() {
		return patente;
	}

	public void setPatente(Patente patente) {
		this.patente = patente;
	}
	
	

}

	
	
