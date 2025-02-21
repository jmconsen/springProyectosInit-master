package es.nebrija.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.nebrija.model.Tarea;

import org.springframework.stereotype.Repository;


@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
	List<Tarea> findByTituloContainingIgnoreCase(String titulo);

}
