
package com.gestion.empleados.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gestion.empleados.entidades.Producto;

public interface ProductoRepository extends PagingAndSortingRepository<Producto, Long>{

	
	@Query("SELECT e FROM Empleado e WHERE e.nombre LIKE %?1%")
	public List<Producto> findAll(String palabraClave);
}
