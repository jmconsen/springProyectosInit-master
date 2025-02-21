package es.nebrija.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.nebrija.model.Proyecto;
import es.nebrija.model.Tarea;
import es.nebrija.service.ProyectoService;
import es.nebrija.service.TareaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/proyectos")
public class ProyectoAdminController {
	
    private final ProyectoService proyectoService;
    private final TareaService tareaService;

    public ProyectoAdminController(ProyectoService proyectoService, TareaService tareaService) {
        this.proyectoService = proyectoService;
        this.tareaService = tareaService;
    }
    
    @GetMapping
    public String listar(Model model) {
        try {
            List<Proyecto> proyectos = proyectoService.listarProyectos();
            Map<Long, String> tareasPorProyecto = new HashMap<>();
            
            for (Proyecto proyecto : proyectos) {
                List<String> nombresTareas = proyecto.getTareas().stream()
                                                     .map(Tarea::getTitulo)
                                                     .collect(Collectors.toList());
                String tareasString = nombresTareas.isEmpty() ? "Sin tareas" : String.join(", ", nombresTareas);
                tareasPorProyecto.put(proyecto.getId(), tareasString);
            }
            
            model.addAttribute("proyectos", proyectos);
            model.addAttribute("tareasPorProyecto", tareasPorProyecto);
            return "proyecto/index";
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error al cargar los proyectos");
            return "error";
        }
    }

    @GetMapping("/crearPro")
    public String crearForm(Model model) {
        model.addAttribute("proyecto", new Proyecto());
        //model.addAttribute("todasLasTareas", tareaService.listarTareas());
        return "proyecto/crearPro";
    }

    @PostMapping
    public String guardar(@ModelAttribute @Valid Proyecto proyecto, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "proyecto/crearPro";
        }
        proyectoService.agregarProyecto(proyecto);
        redirectAttributes.addFlashAttribute("mensaje", "Proyecto creado con éxito");
        return "redirect:/admin/proyectos";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        try {
            Proyecto proyecto = proyectoService.obtenerProyectoPorId(id);
            
            String fechaFormateada = proyecto.getFecha_inicio().toString();
            
            model.addAttribute("proyecto", proyecto);
            model.addAttribute("fechaInicioFormateada", fechaFormateada);
            
            return "proyecto/editar";
        } catch (Exception e) {
            model.addAttribute("error", "Proyecto no encontrado");
            return "error";
        }
    }
    


    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute @Valid Proyecto proyecto, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "proyecto/editar";
        }
        proyectoService.actualizarProyecto(id, proyecto);
        redirectAttributes.addFlashAttribute("mensaje", "Proyecto actualizado con éxito");
        return "redirect:/admin/proyectos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        proyectoService.eliminarProyecto(id);
        return "redirect:/user/proyectos";
    }
    
    /*
    @GetMapping("/buscar")
    public String buscarProyecto(@RequestParam("nombre") String nombre, Model model) {
        List<Proyecto> proyectos = proyectoService.buscarPorNombre(nombre);
        model.addAttribute("proyectos", proyectos);
        return "proyecto/index"; 
    }
    */
    
    @GetMapping("/buscar")
    public String buscarProyecto(@RequestParam("nombre") String nombre, Model model) {
        List<Proyecto> proyectos = proyectoService.buscarPorNombre(nombre);
        Map<Long, Integer> tareasPorProyecto = new HashMap<>();
        for (Proyecto proyecto : proyectos) {
            tareasPorProyecto.put(proyecto.getId(), proyecto.getTareas().size());
        }
        model.addAttribute("proyectos", proyectos);
        model.addAttribute("tareasPorProyecto", tareasPorProyecto);
        return "proyecto/index"; 
    }
    
    

}
