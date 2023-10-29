package pe.edu.cibertec.DAWII_CL2_GabrielVega.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.cibertec.DAWII_CL2_GabrielVega.entity.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol,Integer> {
    Rol findByNomrol (String nombrerol);
}
