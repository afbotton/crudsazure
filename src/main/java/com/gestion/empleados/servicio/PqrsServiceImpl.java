
package com.gestion.empleados.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gestion.empleados.entidades.Pqrs;
import com.gestion.empleados.repositorios.PqrsRepository;

@Service
public class PqrsServiceImpl implements PqrsService {

	@Autowired
	private PqrsRepository pqrsRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Pqrs> ListAll(String palabraClave) {
		if(palabraClave != null) {
			return pqrsRepository.findAll(palabraClave);
		}
		return (List<Pqrs>) pqrsRepository.findAll();
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public Page<Pqrs> findAll(Pageable pageable) {
		return pqrsRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Pqrs pqrs) {
		pqrsRepository.save(pqrs);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		pqrsRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Pqrs findOne(Long id) {
		return pqrsRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Pqrs> findAll() {
		return (List<Pqrs>) pqrsRepository.findAll();
	}
	
}
