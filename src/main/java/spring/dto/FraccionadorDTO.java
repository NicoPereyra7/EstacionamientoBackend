package spring.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class FraccionadorDTO implements Serializable  {
	
	private Long id;
	@NotBlank
	private int minutos;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getMinutos() {
		return minutos;
	}
	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}
	
	

}
