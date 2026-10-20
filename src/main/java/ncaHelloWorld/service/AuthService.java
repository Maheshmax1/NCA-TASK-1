
package ncaHelloWorld.service;

import ncaHelloWorld.dto.RegisterRequest;
import ncaHelloWorld.dto.LoginRequest;
import ncaHelloWorld.dto.LoginResponse;
import ncaHelloWorld.entity.Person;
import ncaHelloWorld.repository.PersonRepository;
import ncaHelloWorld.security.JwtService;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            PersonRepository personRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // REGISTER
    public Person register(RegisterRequest request) {

        if (request.getName() == null
                || request.getName().isBlank()
                || request.getEmail() == null
                || request.getEmail().isBlank()
                || request.getPassword() == null
                || request.getPassword().isBlank()) {

            throw new IllegalArgumentException(
                    "Name, email, and password are required");
        }

        String email = request.getEmail().trim();

        if (personRepository.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException(
                    "Email already registered");
        }

        String role = request.getRole();

        if (role == null || role.isBlank()) {
            role = "USER";
        }

        if (!role.equalsIgnoreCase("USER")
                && !role.equalsIgnoreCase("ADMIN")) {

            throw new IllegalArgumentException(
                    "Role must be USER or ADMIN");
        }

        Person person = new Person();

        person.setName(request.getName().trim());
        person.setEmail(email);
        person.setPassword(
                passwordEncoder.encode(request.getPassword()));
        person.setRole(role);
        person.setAge(request.getAge());

        return personRepository.save(person);
    }

    // LOGIN
    public LoginResponse login(LoginRequest request) {

        if (request.getEmail() == null
                || request.getEmail().isBlank()
                || request.getPassword() == null
                || request.getPassword().isBlank()) {

            throw new BadCredentialsException(
                    "Invalid email or password");
        }

        Person person = personRepository
                .findByEmailIgnoreCase(
                        request.getEmail().trim())
                .orElseThrow(() ->
                        new BadCredentialsException(
                                "Invalid email or password"));

        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                person.getPassword());

        if (!passwordMatches) {
            throw new BadCredentialsException(
                    "Invalid email or password");
        }

        String token = jwtService.generateToken(
                person.getEmail(),
                person.getRole());

        return new LoginResponse(
                "Login successful",
                token,
                "Bearer",
                person.getEmail(),
                person.getRole());
    }
}
