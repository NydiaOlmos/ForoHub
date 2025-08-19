package com.naomi.ForoHub.domain.usuario;


import com.naomi.ForoHub.domain.rol.Rol;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    @Setter
    private String contrasena;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_rol",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private List<Rol> roles = new ArrayList<>();

    public Usuario(@Valid DatosRegistroUsuario datos, Rol rol) {
        this.id = null;
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.contrasena = datos.contrasena();
        this.roles.add(rol);
    }

    public void actualizar(DatosActualizacionUsuario datos) {
        if (datos.nombre() != null) {
            this.nombre = datos.nombre();
        }
        if (datos.contasena() != null) {
            this.contrasena = datos.contasena();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
