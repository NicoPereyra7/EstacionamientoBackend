package spring.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PatenteDTO implements Serializable {

	private Long id;
	@NotBlank
	private String patente;
	private Long usuario_id;
	
	public PatenteDTO() {
		super();
	}
	public PatenteDTO(Long id, @NotBlank String patente) {
		super();
		this.id = id;
		this.patente = patente;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
	
	
	
	
	
	
	
}
