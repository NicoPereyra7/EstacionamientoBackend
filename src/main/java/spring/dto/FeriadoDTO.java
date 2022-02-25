package spring.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class FeriadoDTO implements Serializable {
	private Long id;
	@NotBlank
	private String fecha;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	
}
