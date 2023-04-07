package com.gestion.empleados.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gestion.empleados.entidades.Pqrs;


public interface PqrsService {

	public List<Pqrs> findAll();
	

	public Page<Pqrs> findAll(Pageable pageable);

	public void save(Pqrs pqrs);

	public Pqrs findOne(Long id);

	public void delete(Long id);


	List<Pqrs> ListAll(String palabraClave);


	
}
