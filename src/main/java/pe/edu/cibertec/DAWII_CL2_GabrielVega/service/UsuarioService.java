package pe.edu.cibertec.DAWII_CL2_GabrielVega.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.DAWII_CL2_GabrielVega.entity.Rol;
import pe.edu.cibertec.DAWII_CL2_GabrielVega.entity.Usuario;
import pe.edu.cibertec.DAWII_CL2_GabrielVega.repository.RolRepository;
import pe.edu.cibertec.DAWII_CL2_GabrielVega.repository.UsuarioRepository;

import java.util.Arrays;
import java.util.HashSet;

@Service
@AllArgsConstructor
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private RolRepository rolRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private BCryptPasswordEncoder  bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private Usuario findUserByEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public Usuario findUserByUserName(String username){
        return usuarioRepository.findByNomusuario(username);
    }

    //Para q el codigo se encripte
    public Usuario saveUser(Usuario usuario){
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        usuario.setActivo(true);
        Rol usuarioRol = rolRepository.findByNomrol("ADMIN");
        usuario.setRoles(new HashSet<>(Arrays.asList(usuarioRol)));
        return usuarioRepository.save(usuario);
    }

    public boolean validarContrasena(String username, String contrasena) {
        Usuario usuario = usuarioRepository.findUserByUserName(username);

        if (usuario != null) {
            // Comparar la contraseña ingresada con la contraseña almacenada
            return bCryptPasswordEncoder.matches(contrasena, usuario.getPassword());
        } else {
            return false; // El usuario no existe
        }
    }
    public void actualizarContrasena(String username, String nuevaContrasena) {
        // Obtener el usuario de la base de datos
        Usuario usuario = usuarioRepository.findUserByUserName(username);

        // Hashear la nueva contraseña antes de guardarla en la base de datos
        String nuevaContrasenaHasheada = passwordEncoder.encode(nuevaContrasena);

        // Actualizar la contraseña del usuario
        usuario.setPassword(nuevaContrasenaHasheada);
        usuarioRepository.save(usuario);
    }

}
