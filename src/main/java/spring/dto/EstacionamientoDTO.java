package spring.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EstacionamientoDTO implements Serializable {
	
	private Long id;
	private String horaInicio;
	private String horaFin;	
	private double total;
	@NotNull
	private String patente;	
	private Long usuario_id;
	
	
	public EstacionamientoDTO() {			
	}	
	
	public EstacionamientoDTO(Long id,String horaInicio, double costo, @NotNull String patente, @NotNull Long usuario_id) {
		super();
		this.id = id;
		this.horaInicio = horaInicio;
		this.patente = patente;
		this.usuario_id = usuario_id;
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
	
	public String getPatente() {
		return patente;
	}	
	public void setPatente(String patente) {
		this.patente = patente;
	}
	public Long getUsuario_id() {
		return usuario_id;
	}
	public void setUsuario_id(Long usuario_id) {
		this.usuario_id = usuario_id;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	

}
