
package com.gestion.empleados.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gestion.empleados.entidades.Servicio;
import com.gestion.empleados.repositorios.ServicioRepository;

@Service
public class ServicioServiceImpl implements ServicioService {

	@Autowired
	private ServicioRepository servicioRepository;

	
	
	@Override
	@Transactional(readOnly = true)
	public List<Servicio> ListAll(String palabraClave) {
		if(palabraClave != null) {
			return servicioRepository.findAll(palabraClave);
		}
		return (List<Servicio>) servicioRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Servicio> findAll(Pageable pageable) {
		return servicioRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Servicio servicio) {
		servicioRepository.save(servicio);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		servicioRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Servicio findOne(Long id) {
		return servicioRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findAll() {
		return (List<Servicio>) servicioRepository.findAll();
	}
	
}
