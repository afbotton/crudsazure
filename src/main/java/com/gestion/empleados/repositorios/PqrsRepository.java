
package com.gestion.empleados.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gestion.empleados.entidades.Pqrs;

public interface PqrsRepository extends PagingAndSortingRepository<Pqrs, Long>{

	
	@Query("SELECT e FROM Empleado e WHERE e.nombre LIKE %?1%")
	public List<Pqrs> findAll(String palabraClave);
}
