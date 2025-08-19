package com.naomi.ForoHub.domain.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByEmail(String email);

    Usuario getReferenceByIdAndActivoTrue(Long id);

    @Query("SELECT u FROM Usuario u WHERE u.activo = true")
    Page<Usuario> findAllActivos(Pageable paginacion);
}
