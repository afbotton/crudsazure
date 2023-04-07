
package com.gestion.empleados.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gestion.empleados.entidades.Venta;

public interface VentaService {

	public List<Venta> findAll();
	

	public Page<Venta> findAll(Pageable pageable);

	public void save(Venta venta);

	public Venta findOne(Long id);

	public void delete(Long id);


	


	
}
