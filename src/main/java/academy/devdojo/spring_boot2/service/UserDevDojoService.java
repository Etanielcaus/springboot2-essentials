package academy.devdojo.spring_boot2.service;

import academy.devdojo.spring_boot2.repository.UserDevDojoRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDevDojoService implements UserDetailsService{

    private final UserDevDojoRepository userDevDojoRepository;

    @Override
    public UserDetails loadUserByUsername(String username)  {
        return Optional.ofNullable(userDevDojoRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
