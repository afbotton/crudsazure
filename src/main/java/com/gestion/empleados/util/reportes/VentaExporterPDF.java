
package com.gestion.empleados.util.reportes;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.gestion.empleados.entidades.Venta;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class VentaExporterPDF {

	private List<Venta> listaVenta;

	public VentaExporterPDF(List<Venta> listaVenta) {
		super();
		this.listaVenta = listaVenta;
	}

	private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
		PdfPCell celda = new PdfPCell();

		celda.setBackgroundColor(Color.BLUE);
		celda.setPadding(5);

		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.WHITE);

		celda.setPhrase(new Phrase("ID", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("NombreProducto", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("FechaVenta", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Cantidad", fuente));
		tabla.addCell(celda);
		
		
		celda.setPhrase(new Phrase("Grosor", fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Descripcion", fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("ValorTotal", fuente));
		tabla.addCell(celda);



	}

	private void escribirDatosDeLaTabla(PdfPTable tabla) {
		for (Venta venta : listaVenta) {
			tabla.addCell(String.valueOf(venta.getId()));
			tabla.addCell(venta.getNombreproducto());
			tabla.addCell(venta.getFechaventa().toString());
			tabla.addCell(venta.getDescripcion());
			tabla.addCell(String.valueOf(venta.getCantidad()));
			tabla.addCell(String.valueOf(venta.getGrosor()));
			tabla.addCell(String.valueOf(venta.getValortotal()));
			
		}
	}

	public void exportar(HttpServletResponse response) throws DocumentException, IOException {
		Document documento = new Document(PageSize.A4);
		PdfWriter.getInstance(documento, response.getOutputStream());

		documento.open();

		Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuente.setColor(Color.BLACK);
		fuente.setSize(18);

		Paragraph titulo = new Paragraph("Lista de Ventas", fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);

		PdfPTable tabla = new PdfPTable(7);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setWidths(new float[] {1f, 2.3f, 2.3f, 6f, 2.9f, 3.5f, 2f });
		tabla.setWidthPercentage(110);

		escribirCabeceraDeLaTabla(tabla);
		escribirDatosDeLaTabla(tabla);

		documento.add(tabla);
		documento.close();
	}
}
