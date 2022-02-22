package api.service;

import api.dto.DevDataDTO;
import api.dto.LoginDTO;
import api.exception.CustomException;
import api.exception.DeveloperNotFoundException;
import api.model.Developer;
import api.repository.DeveloperRepository;
import api.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class DeveloperService {

    private final DeveloperRepository developerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity login(LoginDTO loginDTO) {
        try {
            String username = loginDTO.getUsername();
            String password = loginDTO.getPassword();
            Developer developer = developerRepository.findByUsername(username);
            if (developer == null) {
                throw new DeveloperNotFoundException("Developer with name: " + username + " not found");
            }
            String token = jwtTokenProvider.createToken(username, developer.getIssuer(), developer.getSubject(), developer.getRoles());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String signup(Developer developer) {

        if (!developerRepository.existsByUsername(developer.getUsername())) {
            developer.setPassword(passwordEncoder.encode(developer.getPassword()));
            developerRepository.save(developer);
            return jwtTokenProvider.createToken(developer.getUsername(), developer.getIssuer(), developer.getSubject(), developer.getRoles());
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void delete(String username) {
        developerRepository.deleteByUsername(username);
    }

    public Developer search(String username) {
        Developer developer = developerRepository.findByUsername(username);
        if (developer == null) {
            throw new CustomException("Developer doesn't exist", HttpStatus.NOT_FOUND);
        }
        return developer;
    }

    public List<Developer> findAll() {
        return developerRepository.findAll();
    }

    public ResponseEntity<Developer> updateDeveloper(@PathVariable long id, @RequestBody DevDataDTO devDataDTO) {
        Developer updateDeveloper = developerRepository.findById(id)
                .orElseThrow(() -> new DeveloperNotFoundException("Developer doesn't exist with id: " + id));
        updateDeveloper.setUsername(devDataDTO.getUsername());
        updateDeveloper.setEmail(devDataDTO.getEmail());
        developerRepository.save(updateDeveloper);
        return ResponseEntity.ok(updateDeveloper);
    }


}
