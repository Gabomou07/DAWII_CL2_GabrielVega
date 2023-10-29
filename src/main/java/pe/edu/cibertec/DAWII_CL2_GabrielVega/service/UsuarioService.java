package pe.edu.cibertec.DAWII_CL2_GabrielVega.service;

import jakarta.transaction.Transactional;
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
import java.util.Optional;

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



}
