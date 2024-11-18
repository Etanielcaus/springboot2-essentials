package academy.devdojo.spring_boot2.repository;

import academy.devdojo.spring_boot2.dominio.Anime;
import academy.devdojo.spring_boot2.dominio.UserDevDojo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDevDojoRepository extends JpaRepository<UserDevDojo, Long> {

    UserDevDojo findByUsername(String username);
}
