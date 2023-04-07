
package com.gestion.empleados.repositorios;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.gestion.empleados.entidades.Venta;

public interface VentaRepository extends PagingAndSortingRepository<Venta, Long>{

}
