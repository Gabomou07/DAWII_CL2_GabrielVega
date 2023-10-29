package pe.edu.cibertec.DAWII_CL2_GabrielVega.controller.frontoffice;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.DAWII_CL2_GabrielVega.entity.Usuario;
import pe.edu.cibertec.DAWII_CL2_GabrielVega.service.UsuarioService;

@AllArgsConstructor
@Controller
@RequestMapping("/auth")
public class LoginController {

    private UsuarioService usuarioService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String login(){
        return "frontoffice/auth/frmLogin";
    }

    @GetMapping("/registrar")
    public String registrar(){
        return "frontoffice/auth/frmRegistroUsuario";
    }

    @PostMapping("/login-success")
    public String loginSucces (HttpServletRequest request){
        UserDetails usuario = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        HttpSession session = request.getSession();
        session.setAttribute("usuario",usuario.getUsername());
        return "frontoffice/auth/home";
    }


    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@ModelAttribute Usuario usuario){
        usuarioService.saveUser(usuario);
        return "frontoffice/auth/frmLogin";
    }

    @GetMapping("/cambiar")
    public String cambiar(){
        return "frontoffice/auth/frmCambiarUsuario";
    }

    @PostMapping("/cambiar-contrasena")
    public String cambiarContrasena(@RequestParam String contrasenaActual, @RequestParam String nuevaContrasena, Principal principal) {
        String username = contrasenaActual.ge(); // Obtiene el nombre de usuario del usuario autenticado

        // Valida la contraseña actual
        if (usuarioService.validarContrasena(username, contrasenaActual)) {
            // Hashea y almacena la nueva contraseña

            String nuevaContrasena
            String nuevaContrasenaHasheada = passwordEncoder.encode(nuevaContrasena);
            usuarioService.actualizarContrasena(username, nuevaContrasenaHasheada);


            usuarioService.actualizarContrasena(username, nuevaContrasena
// Redirige al usuario a una página de éxito
            return "exito";
        } else {
            // La contraseña actual es incorrecta, redirige a una página de error
            return "error";
        }
    }

}
