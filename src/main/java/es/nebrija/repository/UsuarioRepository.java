package es.nebrija.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.nebrija.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}
