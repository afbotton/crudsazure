package com.gestion.empleados.entidades;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;




@Entity
@Table(name = "Pqrs")
public class Pqrs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String tipopqrs;
	
	@NotEmpty
	private String estadopqrs;
	@NotEmpty
	private String descripcion;
	


	public Pqrs() {
		super();
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getTipopqrs() {
		return tipopqrs;
	}



	public void setTipopqrs(String tipopqrs) {
		this.tipopqrs = tipopqrs;
	}



	public String getEstadopqrs() {
		return estadopqrs;
	}



	public void setEstadopqrs(String estadopqrs) {
		this.estadopqrs = estadopqrs;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	





	
}
