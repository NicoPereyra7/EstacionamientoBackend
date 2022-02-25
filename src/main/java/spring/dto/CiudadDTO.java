package spring.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class CiudadDTO implements Serializable {

	private Long id;
	@NotBlank
	private String horarioInicio;
	@NotBlank
	private String horarioFin;
	@NotBlank
	private double precio;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	public String getHorarioInicio() {
		return horarioInicio;
	}
	public void setHorarioInicio(String horarioInicio) {
		this.horarioInicio = horarioInicio;
	}
	public String getHorarioFin() {
		return horarioFin;
	}
	public void setHorarioFin(String horarioFin) {
		this.horarioFin = horarioFin;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	
}
