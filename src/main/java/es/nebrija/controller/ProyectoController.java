package es.nebrija.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.nebrija.model.Proyecto;
import es.nebrija.model.Tarea;
import es.nebrija.service.ProyectoService;
import es.nebrija.service.TareaService;


@Controller
@RequestMapping("/user/proyectos")
public class ProyectoController {
	
    private final ProyectoService proyectoService;
    private final TareaService tareaService;

    public ProyectoController(ProyectoService proyectoService, TareaService tareaService) {
        this.proyectoService = proyectoService;
        this.tareaService = tareaService;
    }

    /*
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("proyectos", proyectoService.listarProyectos());
        return "proyecto/index";
    }
    */
    
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
