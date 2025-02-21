package es.nebrija.service;

import java.util.List;

import org.springframework.stereotype.Service;

import es.nebrija.model.Proyecto;
import es.nebrija.model.Tarea;
import es.nebrija.repository.ProyectoRepository;

@Service
public class ProyectoService {
	
    private final ProyectoRepository proyectoRepository;

    public ProyectoService(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    public List<Proyecto> listarProyectos() {
        return proyectoRepository.findAll();
    }

    public Proyecto obtenerProyectoPorId(Long id) {
        return proyectoRepository.findById(id).orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    }

    public void actualizarProyecto(Long id, Proyecto proyectoActualizado) {
        Proyecto proyecto = obtenerProyectoPorId(id);
        proyecto.setNombre(proyectoActualizado.getNombre());
        proyecto.setDescripcion(proyectoActualizado.getDescripcion());
        proyecto.setFecha_inicio(proyectoActualizado.getFecha_inicio());
        proyecto.setEstado(proyectoActualizado.getEstado());
        //proyecto.setTareas(proyectoActualizado.getTareas());
        proyectoRepository.save(proyecto);
    }

    public void eliminarProyecto(Long id) {
        proyectoRepository.deleteById(id);
    }
    
    public void agregarProyecto(Proyecto proyecto) {
		if (proyecto.getId() == null)
			proyectoRepository.save(proyecto);
	}
    
    public List<Proyecto> buscarPorNombre(String nombre) {
        return proyectoRepository.findByNombreContainingIgnoreCase(nombre);
    }
	
}
