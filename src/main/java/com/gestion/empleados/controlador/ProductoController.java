
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

import com.gestion.empleados.entidades.Producto;
import com.gestion.empleados.servicio.ProductoService;
import com.gestion.empleados.util.paginacion.PageRender;
import com.gestion.empleados.util.reportes.ProductoExporterExcel;
import com.gestion.empleados.util.reportes.ProductoExporterPDF;
import com.lowagie.text.DocumentException;

@Controller
public class ProductoController {

	@Autowired
	private ProductoService productoService;
	
	@GetMapping("/ver2/{id}")
	public String verDetallesDelProducto(@PathVariable(value = "id") Long id,Map<String,Object> modelo,RedirectAttributes flash) {
		Producto producto = productoService.findOne(id);
		if(producto == null) {
			flash.addFlashAttribute("error", "El Producto no existe en la base de datos");
			return "redirect:/listar2";
		}
		
		modelo.put("producto",producto);
		modelo.put("titulo", "Detalles del Producto " + producto.getNombreproducto());
		return "ver2";
	}
	
	@GetMapping({"/listar2",""})
	public String listarProductos(@RequestParam(name = "page",defaultValue = "0") int page,Model modelo) {
		Pageable pageRequest = PageRequest.of(page, 4);
		Page<Producto> productos = productoService.findAll(pageRequest);
		PageRender<Producto> pageRender1 = new PageRender<>("/listar2", productos);
		
		modelo.addAttribute("titulo","Listado de productos");
		modelo.addAttribute("productos",productos);
		modelo.addAttribute("page", pageRender1);
		
		return "listar2";
	}
	
	@GetMapping("/form2")
	public String mostrarFormularioDeRegistrarProducto(Map<String,Object> modelo) {
		Producto producto = new Producto();
		modelo.put("producto", producto);
		modelo.put("titulo", "Registro de Productos");
		return "form2";
	}
	
	@PostMapping("/form2")
	public String guardarProducto(@Valid Producto producto,BindingResult result,Model modelo,RedirectAttributes flash,SessionStatus status) {
		if(result.hasErrors()) {
			modelo.addAttribute("titulo", "Registro de Productos");
			return "form2";
		}
		
		String mensaje = (producto.getId() != null) ? "El producto ha sido editado con exito" : "producto registrado con exito";
		
		productoService.save(producto);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/listar2";
	}
	
	@GetMapping("/form2/{id}")
	public String editarProducto(@PathVariable(value = "id") Long id,Map<String, Object> modelo,RedirectAttributes flash) {
		Producto producto = null;
		if(id > 0) {
			producto = productoService.findOne(id);
			if(producto == null) {
				flash.addFlashAttribute("error", "El ID del Producto no existe en la base de datos");
				return "redirect:/listar2";
			}
		}
		else {
			flash.addFlashAttribute("error", "El ID del Producto no puede ser cero");
			return "redirect:/listar2";
		}
		
		modelo.put("producto",producto);
		modelo.put("titulo", "Edición de Productos");
		return "form2";
	}
	
	@GetMapping("/eliminar2/{id}")
	public String eliminarProducto(@PathVariable(value = "id") Long id,RedirectAttributes flash) {
		if(id > 0) {
			productoService.delete(id);
			flash.addFlashAttribute("success", "Producto eliminado con exito");
		}
		return "redirect:/listar2";
	}
	
	@GetMapping("/exportarPDF2")
	public void exportarListadoDeProductosEnPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf1");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Productos_" + fechaActual + ".pdf";
		
		response.setHeader(cabecera, valor);
		
		List<Producto> productos = productoService.findAll();
		
		ProductoExporterPDF exporter = new ProductoExporterPDF(productos);
		exporter.exportar(response);
	}
	
	@GetMapping("/exportarExcel2")
	public void exportarListadoDeProductosEnExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octet-stream");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Productos_" + fechaActual + ".xlsx";
		
		response.setHeader(cabecera, valor);
		
		List<Producto> productos = productoService.findAll();
		
		ProductoExporterExcel exporter = new ProductoExporterExcel(productos);
		exporter.exportar(response);
		
		
	}
}
