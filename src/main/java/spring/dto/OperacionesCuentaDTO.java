package spring.dto;

import java.io.Serializable;

import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import spring.modelo.CuentaCorriente;

public class OperacionesCuentaDTO  implements Serializable {
	private Long id;
	@NotBlank
	private String tipoMovimiento;
	@NotBlank
	private String fecha;
	@NotBlank
	private double monto;
	@NotBlank
	private double saldoPrevio;
	
	@JsonIgnore
	@OneToOne()
	private CuentaCorriente cuentaCorriente;

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
		return cuentaCorriente;
	}

	public void setCuentaCorriente(CuentaCorriente cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}
	
	

}
