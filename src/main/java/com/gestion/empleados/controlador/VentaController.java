
package com.gestion.empleados.controlador;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestion.empleados.entidades.Venta;
import com.gestion.empleados.servicio.VentaService;
import com.gestion.empleados.util.paginacion.PageRender;
import com.gestion.empleados.util.reportes.VentaExporterExcel;
import com.gestion.empleados.util.reportes.VentaExporterPDF;
import com.lowagie.text.DocumentException;

@Controller
public class VentaController {

	@Autowired
	private VentaService ventaService;
	
	@GetMapping("/ver1/{id}")
	public String verDetallesDeLaVenta(@PathVariable(value = "id") Long id,Map<String,Object> modelo,RedirectAttributes flash) {
		Venta venta = ventaService.findOne(id);
		if(venta == null) {
			flash.addFlashAttribute("error", "La Venta no existe en la base de datos");
			return "redirect:/listar1";
		}
		
		modelo.put("venta",venta);
		modelo.put("titulo", "Detalles de la Venta " + venta.getNombreproducto());
		return "ver1";
	}
	
	@GetMapping({"/listar1",""})
	public String listarVenta(@RequestParam(name = "page",defaultValue = "0") int page,Model modelo) {
		Pageable pageRequest = PageRequest.of(page, 4);
		Page<Venta> venta = ventaService.findAll(pageRequest);
		PageRender<Venta> pageRender1 = new PageRender<>("/listar1", venta);
		
		modelo.addAttribute("titulo","Listado de Ventas");
		modelo.addAttribute("venta",venta);
		modelo.addAttribute("page", pageRender1);
		
		return "listar1";
	}
	
	@GetMapping("/form1")
	public String mostrarFormularioDeRegistrarVenta(Map<String,Object> modelo) {
		Venta venta = new Venta();
		modelo.put("venta", venta);
		modelo.put("titulo", "Registro de venta");
		return "form1";
	}
	
	@PostMapping("/form1")
	public String guardarVenta(@Valid Venta venta,BindingResult result,Model modelo,RedirectAttributes flash,SessionStatus status) {
		if(result.hasErrors()) {
			modelo.addAttribute("titulo", "Registro de Ventas");
			return "form1";
		}
		
		String mensaje = (venta.getId() != null) ? "La Venta ha sido editado con exito" : "Venta registrado con exito";
		
		ventaService.save(venta);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/listar1";
	}
	
	@GetMapping("/form1/{id}")
	public String editarVenta(@PathVariable(value = "id") Long id,Map<String, Object> modelo,RedirectAttributes flash) {
		Venta venta = null;
		if(id > 0) {
			venta = ventaService.findOne(id);
			if(venta == null) {
				flash.addFlashAttribute("error", "El ID de la Venta no existe en la base de datos");
				return "redirect:/listar1";
			}
		}
		else {
			flash.addFlashAttribute("error", "El ID de la Venta no puede ser cero");
			return "redirect:/listar1";
		}
		
		modelo.put("venta",venta);
		modelo.put("titulo", "Edición de Ventas");
		return "form1";
	}
	
	@GetMapping("/eliminar1/{id}")
	public String eliminarVenta(@PathVariable(value = "id") Long id,RedirectAttributes flash) {
		if(id > 0) {
			ventaService.delete(id);
			flash.addFlashAttribute("success", "Venta eliminada con exito");
		}
		return "redirect:/listar1";
	}
	
	@GetMapping("/exportarPDF1")
	public void exportarListadoDeVentaEnPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf1");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Servicio_" + fechaActual + ".pdf";
		
		response.setHeader(cabecera, valor);
		
		List<Venta> servicio = ventaService.findAll();
		
		VentaExporterPDF exporter = new VentaExporterPDF(servicio);
		exporter.exportar(response);
	}
	
	@GetMapping("/exportarExcel1")
	public void exportarListadoDeVentaEnExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octet-stream");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Servicio_" + fechaActual + ".xlsx";
		
		response.setHeader(cabecera, valor);
		
		List<Venta> venta = ventaService.findAll();
		
		VentaExporterExcel exporter = new VentaExporterExcel(venta);
		exporter.exportar(response);
		
		
	}
}