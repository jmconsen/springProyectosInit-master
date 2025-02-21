package es.nebrija.model;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "El nombre es obligatorio")
    private String nombre;
    
    private String descripcion;
    
    @NotNull(message = "La fecha de inicio es obligatoria")
    //private DateFormat fecha_inicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_inicio;
    
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarea> tareas;// = new ArrayList<>();

    public Proyecto() {}

    public Proyecto(String nombre, String descripcion, LocalDate fecha_inicio, Estado estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha_inicio = fecha_inicio;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public Estado getEstado() {
        return estado;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public enum Estado {
        ACTIVO,
        EN_PROGRESO,
        FINALIZADO
    }
}
