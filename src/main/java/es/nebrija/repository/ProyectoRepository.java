package es.nebrija.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.nebrija.model.Proyecto;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
	List<Proyecto> findByNombreContainingIgnoreCase(String nombre);
}
