
package com.gestion.empleados.entidades;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;






@Entity
@Table(name = "Ventas")
public class Venta{

	
	

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@NotEmpty
		private String nombreproducto;

		@NotNull
		@Temporal(TemporalType.DATE)
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date fechaventa;

		
		@NotNull
		private int cantidad;
		
		
		
	
		
		@NotNull
		private int grosor;
		

		@NotEmpty
		private String descripcion;
		
		
		@NotNull
		private int valortotal;
		
		
		public Venta() {
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


		public Date getFechaventa() {
			return fechaventa;
		}


		public void setFechaventa(Date fechaventa) {
			this.fechaventa = fechaventa;
		}


		public int getCantidad() {
			return cantidad;
		}


		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}


		public int getGrosor() {
			return grosor;
		}


		public void setGrosor(int grosor) {
			this.grosor = grosor;
		}


		public String getDescripcion() {
			return descripcion;
		}


		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}


		public int getValortotal() {
			return valortotal;
		}


		public void setValortotal(int valortotal) {
			this.valortotal = valortotal;
		}



		
	
}
