package es.nebrija.service;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.nebrija.model.Proyecto;
import es.nebrija.model.Tarea;
import es.nebrija.repository.TareaRepository;
import es.nebrija.repository.ProyectoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;
    private final ProyectoRepository proyectoRepository;

    //@Autowired
    public TareaService(TareaRepository tareaRepository, ProyectoRepository proyectoRepository) {
        this.tareaRepository = tareaRepository;
        this.proyectoRepository = proyectoRepository;
    }

    public List<Tarea> listarTareas() {
        return tareaRepository.findAll();
    }
    
    public Page<Tarea> listarTareasPaginadas(Pageable pageable) {
        return tareaRepository.findAll(pageable);
    }

    public Tarea obtenerTareaPorId(Long id) {
        return tareaRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
    }

    public void actualizarTarea(Long id, Tarea tareaActualizada) {
        Tarea tarea = obtenerTareaPorId(id);
        tarea.setTitulo(tareaActualizada.getTitulo());
        tarea.setDescripcion(tareaActualizada.getDescripcion());
        tarea.setFecha_limite(tareaActualizada.getFecha_limite());
        tarea.setEstado(tareaActualizada.getEstado());
        tarea.setProyecto(tareaActualizada.getProyecto());
        tareaRepository.save(tarea);
    }

    public void eliminarTarea(Long id) {
        tareaRepository.deleteById(id);
    }
    
    /*
    public Tarea guardarTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }
    */
    
    public void agregarTarea(Tarea tarea) {
		if (tarea.getId() == null)
			tareaRepository.save(tarea);
	}
    
    public List<Tarea> buscarPorTitulo(String titulo) {
        return tareaRepository.findByTituloContainingIgnoreCase(titulo);
    }
    
    /*
    public void agregarTarea(Tarea tarea) {
        if (tarea.getProyecto() != null && tarea.getProyecto().getId() != null) {
            // Buscar el proyecto existente en la base de datos
            Proyecto proyecto = proyectoRepository.findById(tarea.getProyecto().getId())
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
            // Asociar el proyecto existente a la tarea
            tarea.setProyecto(proyecto);
        } else {
            throw new RuntimeException("El proyecto es obligatorio");
        }
        tareaRepository.save(tarea);
    }
    */

    /*
    @Transactional
    public void agregarTarea(Tarea tarea) {
        if (tarea.getProyecto() != null && tarea.getProyecto().getId() != null) {
            Proyecto proyecto = proyectoRepository.findById(tarea.getProyecto().getId())
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
            tarea.setProyecto(proyecto);
        }
        tareaRepository.save(tarea);
    }
    */
    
//    @Transactional
//    public void agregarTarea(Tarea tarea) {
//        if (tarea.getProyecto() != null && tarea.getProyecto().getId() != null) {
//            Proyecto proyecto = proyectoRepository.findById(tarea.getProyecto().getId())
//                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
//            tarea.setProyecto(proyecto);
//        }
//        tareaRepository.save(tarea);
//    }
    
    /*
    public void agregarTarea(Tarea tarea) {
		if (tarea.getId() == null)
			tareaRepository.save(tarea);
	}
	*/
}