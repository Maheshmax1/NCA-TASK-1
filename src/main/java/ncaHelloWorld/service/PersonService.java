
package ncaHelloWorld.service;

import ncaHelloWorld.entity.Person;
import ncaHelloWorld.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // Add person - reject duplicate names
    public Person addPerson(Person person) {
        String name = normalizeName(person.getName());
        person.setName(name);

        if (personRepository.existsByNameIgnoreCase(name)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Person name already exists: " + name
            );
        }

        return personRepository.save(person);
    }

    // Get all persons
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    // Get single person by name
    public Person getPersonByName(String name) {
        return personRepository.findByNameIgnoreCase(normalizeName(name))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Person not found: " + name
                ));
    }

    // Update person using the current name
    public Person updatePersonByName(String currentName, Person updatedPerson) {
        Person existingPerson = getPersonByName(currentName);

        String newName = normalizeName(updatedPerson.getName());

        // Reject a name already used by another person
        personRepository.findByNameIgnoreCase(newName)
                .filter(person -> !person.getId().equals(existingPerson.getId()))
                .ifPresent(person -> {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "Person name already exists: " + newName
                    );
                });

        existingPerson.setName(newName);
        existingPerson.setEmail(updatedPerson.getEmail());
        existingPerson.setAge(updatedPerson.getAge());

        return personRepository.save(existingPerson);
    }

    // Delete person by name
    public void deletePersonByName(String name) {
        Person person = getPersonByName(name);
        personRepository.delete(person);
    }

    // Trim spaces and validate name
    private String normalizeName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Person name cannot be empty"
            );
        }

        return name.trim();
    }
}


