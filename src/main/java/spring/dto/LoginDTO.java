package spring.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginDTO implements Serializable {
	
	@NotBlank
	@Size(min=10, max=10)
	private String telefono;
	@NotBlank
	@Size(min=8, max=40)
	private String clave;
	
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	
}
