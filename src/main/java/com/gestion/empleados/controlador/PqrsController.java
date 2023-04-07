
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

import com.gestion.empleados.entidades.Pqrs;
import com.gestion.empleados.servicio.PqrsService;
import com.gestion.empleados.util.paginacion.PageRender;
import com.gestion.empleados.util.reportes.PqrsExporterExcel;
import com.gestion.empleados.util.reportes.PqrsExporterPDF;
import com.lowagie.text.DocumentException;

@Controller
public class PqrsController {

	@Autowired
	private PqrsService pqrsService;
	
	@GetMapping("/ver4/{id}")
	public String verDetallesDelPqrs(@PathVariable(value = "id") Long id,Map<String,Object> modelo,RedirectAttributes flash) {
		Pqrs pqrs = pqrsService.findOne(id);
		if(pqrs == null) {
			flash.addFlashAttribute("error", "La PQRS no existe en la base de datos");
			return "redirect:/listar4";
		}
		
		modelo.put("pqrs",pqrs);
		modelo.put("titulo", "Detalles de la pqrs " + pqrs.getTipopqrs());
		return "ver4";
	}
	
	@GetMapping({"/listar4",""})
	public String listarPqrs(@RequestParam(name = "page",defaultValue = "0") int page,Model modelo) {
		Pageable pageRequest = PageRequest.of(page, 4);
		Page<Pqrs> pqrs = pqrsService.findAll(pageRequest);
		PageRender<Pqrs> pageRender1 = new PageRender<>("/listar4", pqrs);
		
		modelo.addAttribute("titulo","Listado de pqrs");
		modelo.addAttribute("pqrs",pqrs);
		modelo.addAttribute("page", pageRender1);
		
		return "listar4";
	}
	
	@GetMapping("/form4")
	public String mostrarFormularioDeRegistrarPqrs(Map<String,Object> modelo) {
		Pqrs pqrs = new Pqrs();
		modelo.put("pqrs", pqrs);
		modelo.put("titulo", "Registro de pqrs");
		return "form4";
	}
	
	@PostMapping("/form4")
	public String guardarPqrs(@Valid Pqrs pqrs,BindingResult result,Model modelo,RedirectAttributes flash,SessionStatus status) {
		if(result.hasErrors()) {
			modelo.addAttribute("titulo", "Registro de pqrs");
			return "form4";
		}
		
		String mensaje = (pqrs.getId() != null) ? "La pqrs ha sido editado con exito" : "pqrs registrada con exito";
		
		pqrsService.save(pqrs);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/listar4";
	}
	
	@GetMapping("/form4/{id}")
	public String editarPqrs(@PathVariable(value = "id") Long id,Map<String, Object> modelo,RedirectAttributes flash) {
		Pqrs pqrs = null;
		if(id > 0) {
			pqrs = pqrsService.findOne(id);
			if(pqrs == null) {
				flash.addFlashAttribute("error", "El ID de la pqrs no existe en la base de datos");
				return "redirect:/listar4";
			}
		}
		else {
			flash.addFlashAttribute("error", "El ID del Servicio no puede ser cero");
			return "redirect:/listar3";
		}
		
		modelo.put("pqrs",pqrs);
		modelo.put("titulo", "Edición de pqrs");
		return "form4";
	}
	
	@GetMapping("/eliminar4/{id}")
	public String eliminarPqrs(@PathVariable(value = "id") Long id,RedirectAttributes flash) {
		if(id > 0) {
			pqrsService.delete(id);
			flash.addFlashAttribute("success", "pqrs eliminada con exito");
		}
		return "redirect:/listar4";
	}
	
	@GetMapping("/exportarPDF4")
	public void exportarListadoDePqrsEnPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf1");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Servicio_" + fechaActual + ".pdf";
		
		response.setHeader(cabecera, valor);
		
		List<Pqrs> pqrs = pqrsService.findAll();
		
		PqrsExporterPDF exporter = new PqrsExporterPDF(pqrs);
		exporter.exportar(response);
	}
	
	@GetMapping("/exportarExcel4")
	public void exportarListadoDePqrsEnExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octet-stream");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Servicio_" + fechaActual + ".xlsx";
		
		response.setHeader(cabecera, valor);
		
		List<Pqrs> pqrs = pqrsService.findAll();
		
		PqrsExporterExcel exporter = new PqrsExporterExcel(pqrs);
		exporter.exportar(response);
		
		
	}
}