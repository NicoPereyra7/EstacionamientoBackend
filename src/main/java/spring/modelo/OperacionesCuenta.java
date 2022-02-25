package spring.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name ="operacionesCuenta")
public class OperacionesCuenta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "tipoMovimiento")
	private String tipoMovimiento;
	@Column(name = "fecha")
	private String fecha;
	@Column(name = "monto")
	private double monto;
	@Column(name = "saldoPrevio")
	private double saldoPrevio;
	
	@JsonIgnore
	@ManyToOne()
	private CuentaCorriente cuenta;	

	public OperacionesCuenta() {
		super();
	}
	
	public OperacionesCuenta(String tipoMovimiento, String fecha, double monto, double saldoPrevio,
			CuentaCorriente cuentaCorriente) {
		super();
		this.tipoMovimiento = tipoMovimiento;
		this.fecha = fecha;
		this.monto = monto;
		this.saldoPrevio = saldoPrevio;
		this.cuenta = cuentaCorriente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public double getSaldoPrevio() {
		return saldoPrevio;
	}

	public void setSaldoPrevio(double saldoPrevio) {
		this.saldoPrevio = saldoPrevio;
	}

	public CuentaCorriente getCuentaCorriente() {
		return cuenta;
	}

	public void setCuentaCorriente(CuentaCorriente cuenta) {
		this.cuenta = cuenta;
	}

	
	
	
}
