package com.gestion.empleados.entidades;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;



@Entity
@Table(name = "servicios")
public class Servicio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String tiposervicio;

	@NotNull
	private int precioservicio;


	public Servicio() {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTiposervicio() {
		return tiposervicio;
	}


	public void setTiposervicio(String tiposervicio) {
		this.tiposervicio = tiposervicio;
	}


	public int getPrecioservicio() {
		return precioservicio;
	}


	public void setPrecioservicio(int precioservicio) {
		this.precioservicio = precioservicio;
	}






	
}
