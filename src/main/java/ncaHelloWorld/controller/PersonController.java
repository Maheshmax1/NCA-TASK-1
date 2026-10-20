
package ncaHelloWorld.controller;

import ncaHelloWorld.entity.Person;
import ncaHelloWorld.service.PersonService;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // 1. Only ADMIN can add a person
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addPerson")
    public Person addPerson(@RequestBody Person person) {
        return personService.addPerson(person);
    }

    // 2. Only ADMIN can view all persons
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllPersons")
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    // 3. USER can view their own details.
    // ADMIN can view any person's details.
    @GetMapping("/getSinglePerson/{name}")
    public Person getSinglePerson(
            @PathVariable String name,
            Authentication authentication) {

        Person person = personService.getPersonByName(name);

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN"));

        // ADMIN can view any person
        if (isAdmin) {
            return person;
        }

        // USER can view only their own record
        if (person.getEmail() != null
                && person.getEmail().equalsIgnoreCase(
                authentication.getName())) {
            return person;
        }

        throw new AccessDeniedException(
                "You are not allowed to view this person's details");
    }

    // 4. Only ADMIN can update a person
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updatePerson/{name}")
    public Person updatePerson(
            @PathVariable String name,
            @RequestBody Person person) {

        return personService.updatePersonByName(name, person);
    }

    // 5. Only ADMIN can delete a person
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletePerson/{name}")
    public String deletePerson(@PathVariable String name) {

        personService.deletePersonByName(name);

        return "Person deleted successfully: " + name;
    }
}
