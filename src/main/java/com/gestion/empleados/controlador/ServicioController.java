
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

import com.gestion.empleados.entidades.Servicio;
import com.gestion.empleados.servicio.ServicioService;
import com.gestion.empleados.util.paginacion.PageRender;
import com.gestion.empleados.util.reportes.ServicioExporterExcel;
import com.gestion.empleados.util.reportes.ServicioExporterPDF;
import com.lowagie.text.DocumentException;

@Controller
public class ServicioController {

	@Autowired
	private ServicioService servicioService;
	
	@GetMapping("/ver3/{id}")
	public String verDetallesDelServicio(@PathVariable(value = "id") Long id,Map<String,Object> modelo,RedirectAttributes flash) {
		Servicio servicio = servicioService.findOne(id);
		if(servicio == null) {
			flash.addFlashAttribute("error", "El Servicio no existe en la base de datos");
			return "redirect:/listar3";
		}
		
		modelo.put("servicio",servicio);
		modelo.put("titulo", "Detalles del Servicio " + servicio.getTiposervicio());
		return "ver3";
	}
	
	@GetMapping({"/listar3",""})
	public String listarServicio(@RequestParam(name = "page",defaultValue = "0") int page,Model modelo) {
		Pageable pageRequest = PageRequest.of(page, 4);
		Page<Servicio> servicio = servicioService.findAll(pageRequest);
		PageRender<Servicio> pageRender1 = new PageRender<>("/listar3", servicio);
		
		modelo.addAttribute("titulo","Listado de Servicios");
		modelo.addAttribute("servicio",servicio);
		modelo.addAttribute("page", pageRender1);
		
		return "listar3";
	}
	
	@GetMapping("/form3")
	public String mostrarFormularioDeRegistrarServicio(Map<String,Object> modelo) {
		Servicio servicio = new Servicio();
		modelo.put("servicio", servicio);
		modelo.put("titulo", "Registro de servicios");
		return "form3";
	}
	
	@PostMapping("/form3")
	public String guardarServicio(@Valid Servicio servicio,BindingResult result,Model modelo,RedirectAttributes flash,SessionStatus status) {
		if(result.hasErrors()) {
			modelo.addAttribute("titulo", "Registro de Servicios");
			return "form3";
		}
		
		String mensaje = (servicio.getId() != null) ? "El Servicio ha sido editado con exito" : "servicio registrado con exito";
		
		servicioService.save(servicio);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/listar3";
	}
	
	@GetMapping("/form3/{id}")
	public String editarServicio(@PathVariable(value = "id") Long id,Map<String, Object> modelo,RedirectAttributes flash) {
		Servicio servicio = null;
		if(id > 0) {
			servicio = servicioService.findOne(id);
			if(servicio == null) {
				flash.addFlashAttribute("error", "El ID del Servicio no existe en la base de datos");
				return "redirect:/listar3";
			}
		}
		else {
			flash.addFlashAttribute("error", "El ID del Servicio no puede ser cero");
			return "redirect:/listar3";
		}
		
		modelo.put("servicio",servicio);
		modelo.put("titulo", "Edición de Servicios");
		return "form3";
	}
	
	@GetMapping("/eliminar3/{id}")
	public String eliminarServicio(@PathVariable(value = "id") Long id,RedirectAttributes flash) {
		if(id > 0) {
			servicioService.delete(id);
			flash.addFlashAttribute("success", "Servicio eliminado con exito");
		}
		return "redirect:/listar3";
	}
	
	@GetMapping("/exportarPDF3")
	public void exportarListadoDeServicioEnPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf1");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Servicio_" + fechaActual + ".pdf";
		
		response.setHeader(cabecera, valor);
		
		List<Servicio> servicio = servicioService.findAll();
		
		ServicioExporterPDF exporter = new ServicioExporterPDF(servicio);
		exporter.exportar(response);
	}
	
	@GetMapping("/exportarExcel3")
	public void exportarListadoDeServicioEnExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octet-stream");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Servicio_" + fechaActual + ".xlsx";
		
		response.setHeader(cabecera, valor);
		
		List<Servicio> servicio = servicioService.findAll();
		
		ServicioExporterExcel exporter = new ServicioExporterExcel(servicio);
		exporter.exportar(response);
		
		
	}
}