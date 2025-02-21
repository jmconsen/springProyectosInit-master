package es.nebrija.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.nebrija.model.Tarea;
import es.nebrija.service.ProyectoService;
import es.nebrija.service.TareaService;

@Controller
@RequestMapping("/user/tareas")
public class TareaController {

    private final TareaService tareaService;
    private final ProyectoService proyectoService;

    public TareaController(TareaService tareaService, ProyectoService proyectoService) {
        this.tareaService = tareaService;
        this.proyectoService = proyectoService;
    }

    @GetMapping
    public String listar(Model model, 
                         @RequestParam(defaultValue = "0") int page, 
                         @RequestParam(defaultValue = "10") int size) {
        try {
            Page<Tarea> tareaPage = tareaService.listarTareasPaginadas(PageRequest.of(page, size));
            model.addAttribute("tareas", tareaPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", tareaPage.getTotalPages());
            return "tarea/indexTarea";
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error al cargar las tareas");
            return "error";
        }
    }
}

