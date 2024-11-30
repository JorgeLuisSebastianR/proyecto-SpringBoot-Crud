package com.tcna.gestioncursos.controller;

import com.tcna.gestioncursos.entity.Curso;
import com.tcna.gestioncursos.reports.CursoExporterExcel;
import com.tcna.gestioncursos.reports.CursoExporterPDF;
import com.tcna.gestioncursos.repository.CursoRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public String home() {
        return "redirect:/cursos";
    }

    @GetMapping("/cursos")
    public String listarCursos(Model model, @Param("keyword") String keyword, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size) {
        try {
            List<Curso> cursos = new  ArrayList<>();
            Pageable paging = PageRequest.of(page-1, size);
            Page<Curso> pegaCurso = null;
            if(keyword == null) {
                pegaCurso = cursoRepository.findAll(paging);

            }else{
                pegaCurso = cursoRepository.findCursoByTituloContainingIgnoreCase(keyword, paging);
                model.addAttribute("keyword", keyword);
            }
            System.out.println(cursos.size());
            System.out.println(pegaCurso.getTotalElements());
            System.out.println(pegaCurso.getTotalPages());
            System.out.println(size);
            System.out.println(page);
            cursos = pegaCurso.getContent();
            model.addAttribute("cursos", cursos);
            model.addAttribute("currentPage", pegaCurso.getNumber() + 1);
            model.addAttribute("totalItems", pegaCurso.getTotalElements());
            model.addAttribute("totalPages", pegaCurso.getTotalPages());
            model.addAttribute("pageSize", size);
            model.addAttribute("page", page);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());

        }
        return "cursos";
    }

    @GetMapping("/cursos/nuevo")
    public String mostrarFormularioGuardarCurso(Model model){
        Curso curso = new Curso();
        curso.setPublicado(true);
        model.addAttribute("curso",curso);
        model.addAttribute("pageTitle","Nuevo curso");
        return "curso_form";
    }


    @PostMapping("/cursos/save")
    public String guardarCurso(Curso curso, RedirectAttributes redirectAttributes) {
        try{
            cursoRepository.save(curso);
            redirectAttributes.addFlashAttribute("mensaje", "Curso guardado correctamente");
        }catch (Exception e){
            redirectAttributes.addAttribute("mensaje", "Error al guardar el Curso");
        }
        return "redirect:/cursos";
    }

    @GetMapping("/cursos/{id}")
    public String editarCurso(@PathVariable Integer id,Model model, RedirectAttributes redirectAttributes) {
        try{
            Curso curso = cursoRepository.findById(id).get();
            model.addAttribute("curso", curso);
            model.addAttribute("pageTitle", "Editar Curso" + id);
            return "curso_form";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mensaje", "Error al guardar el Curso");
        }
        return "redirect:/cursos";
    }

    @GetMapping("/cursos/delete/{id}")
    public String eliminarCurso(@PathVariable Integer id,Model model,RedirectAttributes redirectAttributes){
        try{
            cursoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message","El curso ha sido eliminado");
        }catch (Exception exception){
            redirectAttributes.addFlashAttribute("message",exception.getMessage());
        }
        return "redirect:/cursos";
    }

    @GetMapping("/export/pdf")
    public void generarReportePDF(HttpServletResponse response)throws Exception {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=cursos"+ currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<Curso> cursos = cursoRepository.findAll();
        CursoExporterPDF exporterPDF = new CursoExporterPDF(cursos);
        exporterPDF.export(response);

    }

    @GetMapping("/export/excel")
    public void generarReporteExcel(HttpServletResponse response)throws Exception {
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=cursos"+ currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<Curso> cursos = cursoRepository.findAll();

        CursoExporterExcel exporterExcel = new CursoExporterExcel(cursos);
        exporterExcel.export(response);

    }


}
