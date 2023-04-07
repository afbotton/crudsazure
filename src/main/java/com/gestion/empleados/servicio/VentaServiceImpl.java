
package com.gestion.empleados.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.empleados.entidades.Venta;
import com.gestion.empleados.repositorios.VentaRepository;

@Service
public class VentaServiceImpl implements VentaService {

	@Autowired
	private VentaRepository ventaRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Venta> findAll() {
		return (List<Venta>) ventaRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Venta> findAll(Pageable pageable) {
		return ventaRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Venta venta) {
		ventaRepository.save(venta);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		ventaRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Venta findOne(Long id) {
		return ventaRepository.findById(id).orElse(null);
	}

}
