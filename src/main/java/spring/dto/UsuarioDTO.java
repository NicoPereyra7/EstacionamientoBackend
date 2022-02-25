package spring.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UsuarioDTO implements Serializable {

	private Long id;
	@NotBlank
	@Size(max=90, min=5) 
	private String mail;
	@NotBlank
	@Size(min=10, max=10)
	private String telefono;
	@NotBlank
	@Size(min=8, max=40)
	private String clave;
	@JsonIgnore
	private List<PatenteDTO> patentes;
	private CuentaCorrienteDTO cuentaCorriente;
	@JsonIgnore
	private Set<String> roles = new HashSet<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
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
	public List<PatenteDTO> getPatentes() {
		return patentes;
	}
	public void setPatentes(List<PatenteDTO> patentes) {
		this.patentes = patentes;
	}
	public CuentaCorrienteDTO getCuentaCorriente() {
		return cuentaCorriente;
	}
	public void setCuentaCorriente(CuentaCorrienteDTO cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	
	
}
