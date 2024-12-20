/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Proyecto.controller;

import com.example.Proyecto.model.Cliente;
import com.example.Proyecto.service.ClienteService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletResponse;


// importaciones pdf
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import java.io.IOException;
import java.util.List;
import com.itextpdf.layout.Document;


// importaciones excel
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author FLOMAR
 */
@Controller
@RequestMapping("/clientes")
public class ClienteController {
    
     private final ClienteService service;

    // Constructor para inyectar el servicio
    public ClienteController(ClienteService service) {
        this.service = service;
    }

    // Mostrar todos los proveedores
    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", service.listarTodas());
        return "clientes"; // Vista que muestra todos los proveedores
    }

    // Mostrar el formulario para crear un nuevo cliente
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "formulario"; // Vista para el formulario de nuevo cliente
    }

    // Guardar un nuevo cliente
    @PostMapping
    public String guardarCliente(@ModelAttribute Cliente cliente) {
        service.guardar(cliente);
        return "redirect:/clientes"; // Redirigir a la lista de clientes después de guardar
    }

    // Mostrar el formulario para editar un cliente existente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", service.buscarPorId(id).orElseThrow(() ->
                new IllegalArgumentException("ID inválido: " + id)));
        return "formulario"; // Vista para el formulario de edición de cliente
    }

    // Eliminar un cliente por su ID
    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/clientes"; // Redirigir a la lista de clientes después de eliminar
    }
    
    @GetMapping("/reporte/pdf")
    public void generarPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=clientes_reporte.pdf");
        
        PdfWriter write = new PdfWriter(response.getOutputStream());
        Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(write));
        
        document.add(new Paragraph("Reporte de clientes").setBold().setFontSize(18));
        
        Table table = new Table(5);
        table.addCell("ID");
        table.addCell("Nombre");
        table.addCell("Dni");
        table.addCell("Direccion");
        table.addCell("Estado");
        
        List<Cliente> clientes = this.service.listarTodas();
        for (Cliente cliente : clientes) {
            table.addCell(cliente.getId().toString());
            table.addCell(cliente.getNombre());
            table.addCell(cliente.getDni());            
            table.addCell(cliente.getDireccion());
            table.addCell(cliente.getEstado());
        }
        
        document.add(table);
        document.close();
    }
    
    @GetMapping("/reporte/excel")
    public void generarExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=clientes_reporte.xlsx");
        
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Clientes");
        
        Row headerRow = sheet.createRow(0);
        String[] columnHeaders = {"ID","Nombre", "Dni", "Direccion","Estado"};
        for (int i = 0; i < columnHeaders.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnHeaders[i]);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            cell.setCellStyle(style);
        }
        
        List<Cliente> clientes = this.service.listarTodas();
        int rowIndex = 1;
        for (Cliente cliente : clientes) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(cliente.getId());
              row.createCell(1).setCellValue(cliente.getNombre());
            row.createCell(2).setCellValue(cliente.getDni());
            row.createCell(3).setCellValue(cliente.getDireccion());
            row.createCell(4).setCellValue(cliente.getEstado());
        }
        
        /*for (int i = 0; columnHeaders.length; i++) {
            sheet.autoSizeColumn(i);
        }*/
        
        workbook.write(response.getOutputStream());
        workbook.close();
        
    }
    
    
    
}
