package com.gestion.empleados.entidades;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;



@Entity
@Table(name = "productos")
public class Producto{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String nombreproducto;

	@NotEmpty
	private String descripcion;


	@NotNull
	private int totalexistencia;

	@NotNull
	private double preciomt2;


	public Producto() {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNombreproducto() {
		return nombreproducto;
	}


	public void setNombreproducto(String nombreproducto) {
		this.nombreproducto = nombreproducto;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public int getTotalexistencia() {
		return totalexistencia;
	}


	public void setTotalexistencia(int totalexistencia) {
		this.totalexistencia = totalexistencia;
	}


	public double getPreciomt2() {
		return preciomt2;
	}


	public void setPreciomt2(double preciomt2) {
		this.preciomt2 = preciomt2;
	}



	
}
