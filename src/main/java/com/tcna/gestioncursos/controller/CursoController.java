package com.tcna.gestioncursos.controller;

import com.tcna.gestioncursos.entity.Curso;
import com.tcna.gestioncursos.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String listarCursos(Model model) {
        List<Curso> cursos = cursoRepository.findAll();
        cursos = cursoRepository.findAll();
        model.addAttribute("cursos", cursos);
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

//    @GetMapping("/cursos/nuevo")
//    public String agregarCurso(Model model) {
//        Curso curso = new Curso();
//        curso.setPublicado(true);
//        model.addAttribute("curso", curso);
//        model.addAttribute("pageTitle", "Nuevo Curso");
//        return "cursos_from";
//    }

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


}
