package spring.modelo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;


@Entity 
@Table(name= "usuario")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 90, unique = true)
	@NotEmpty
	@Email
	private String mail;
	@Column(length = 10, unique = true)
	@NotEmpty
	private String telefono;
	@Column()
	@NotEmpty
	private String clave;
	@JsonIgnore
	@OneToOne(mappedBy= "usuario", fetch=FetchType.LAZY)
	private CuentaCorriente cuentaCorriente;
	@JsonIgnore
	@OneToMany(mappedBy="usuario", orphanRemoval = true)	
	private List<Patente> patentes;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
	private Set<Rol> roles = new HashSet<>();
	

	public Usuario() {

	}
	
	
	
	public Usuario(String mail, String telefono, String clave) {
		super();
		this.mail = mail;
		this.telefono = telefono;
		this.clave = clave;
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



	public Long getId() {
		return id;
	}

	

	public CuentaCorriente getCuentaCorriente() {
		return cuentaCorriente;
	}



	public void setCuentaCorriente(CuentaCorriente cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}



	public List<Patente> getPatentes() {
		return patentes;
	}



	public void setPatentes(List<Patente> patentes) {
		this.patentes = patentes;
	}



	public void setId(Long id) {
		this.id = id;
	}
	
	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}
	
}
