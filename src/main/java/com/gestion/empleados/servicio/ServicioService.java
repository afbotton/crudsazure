package com.gestion.empleados.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gestion.empleados.entidades.Servicio;


public interface ServicioService {

	public List<Servicio> findAll();
	

	public Page<Servicio> findAll(Pageable pageable);

	public void save(Servicio servicio);

	public Servicio findOne(Long id);

	public void delete(Long id);


	List<Servicio> ListAll(String palabraClave);


	
}
