package es.nebrija.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;

import es.nebrija.model.Proyecto;
import es.nebrija.model.Tarea;
import es.nebrija.service.TareaService;
import es.nebrija.service.ProyectoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


@Controller
@RequestMapping("/admin/tareas")
public class TareaAdminController {

    private final TareaService tareaService;
    private final ProyectoService proyectoService;

    //@Autowired
    public TareaAdminController(TareaService tareaService, ProyectoService proyectoService) {
        this.tareaService = tareaService;
        this.proyectoService = proyectoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tareas", tareaService.listarTareas());
        return "tarea/indexTarea";
    }

    @GetMapping("/crearTarea")
    public String crearForm(Model model) {
        model.addAttribute("tarea", new Tarea());
        model.addAttribute("proyectos", proyectoService.listarProyectos());
        return "tarea/crearTarea";
    }
    
    @PostMapping
    public String guardar(@ModelAttribute Tarea tarea, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "tarea/crearTarea";
        }
        
        Proyecto proyecto = proyectoService.obtenerProyectoPorId(tarea.getProyecto().getId());
        tarea.setProyecto(proyecto);
        
        tareaService.agregarTarea(tarea);
        redirectAttributes.addFlashAttribute("mensaje", "Tarea creada con éxito");
        return "redirect:/admin/tareas";
    }
    
    @GetMapping("/editarTarea/{id}")
    public String editar(@PathVariable Long id, Model model) {
        try {
            Tarea tarea = tareaService.obtenerTareaPorId(id);
            
            String fechaFormateada = tarea.getFecha_limite().toString();
            
            model.addAttribute("tarea", tarea);
            model.addAttribute("proyectos", proyectoService.listarProyectos());
            model.addAttribute("fechaLimiteFormateada", fechaFormateada);
            
            return "tarea/editarTarea";
        } catch (Exception e) {
            model.addAttribute("error", "Tarea no encontrada");
            return "error";
        }
    }
    
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute @Valid Tarea tareaActualizada, BindingResult result, RedirectAttributes redirectAttributes) {
    	if (result.hasErrors()) {
            return "tarea/editarTarea";
        }
    	
    	tareaService.actualizarTarea(id, tareaActualizada);
    	redirectAttributes.addFlashAttribute("mensaje", "Proyecto actualizado con éxito");
        return "redirect:/admin/tareas";
    }
    
    @GetMapping("/eliminarTarea/{id}")
    public String eliminar(@PathVariable Long id) {
        tareaService.eliminarTarea(id);
        return "redirect:/admin/tareas";
    }
    
    @GetMapping("/buscar")
    public String buscarTarea(@RequestParam("titulo") String titulo, Model model) {
        List<Tarea> tareas = tareaService.buscarPorTitulo(titulo);
        model.addAttribute("tareas", tareas);
        return "tarea/indexTarea"; 
    }

    /*
    @PostMapping
    public String guardar(@ModelAttribute Tarea tarea) {
        if (tarea.getProyecto() == null || tarea.getProyecto().getId() == null) {
            return "error";
        }
        Proyecto proyecto = proyectoService.obtenerProyectoPorId(tarea.getProyecto().getId());
        tarea.setProyecto(proyecto);
        tareaService.agregarTarea(tarea);
        return "redirect:/admin/tareas";
    }
    */
    
    
    /*
    @PostMapping
    public String guardar(@ModelAttribute Tarea tarea, Model model) {
        if (tarea.getProyecto() == null || tarea.getProyecto().getId() == null) {
            model.addAttribute("error", "El proyecto es obligatorio");
            return "tarea/crearTarea";
        }
        try {
            tareaService.agregarTarea(tarea);
            return "redirect:/admin/tareas";
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar la tarea: " + e.getMessage());
            return "tarea/crearTarea";
        }
    }
    */
    
    /*
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Tarea tarea, Model model) {
        if (tarea.getProyecto() == null || tarea.getProyecto().getId() == null) {
            model.addAttribute("error", "El proyecto es obligatorio");
            return "tarea/crearTarea";
        }
        try {
            tareaService.agregarTarea(tarea);
            return "redirect:/admin/tareas";
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar la tarea: " + e.getMessage());
            return "tarea/crearTarea";
        }
    }
    */

    
    /*
    @PostMapping("/guardar")
    public String guardarTarea(@ModelAttribute Tarea tarea) {
    	System.out.println(tarea.getProyecto().getId());
        tareaService.guardarTarea(tarea);
        return "redirect:/admin/tareas";
    }
    */
    
    /*
    @PostMapping("/guardar")
    public String guardarTarea(Tarea tarea) {
    	System.out.println(tarea.getProyecto().getId());
        tareaService.agregarTarea(tarea);
        return "redirect:/admin/tareas";
    }*/
}
