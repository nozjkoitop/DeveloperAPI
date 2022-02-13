package api.controller;


import api.exception.DeveloperNotFoundException;
import api.model.Developer;
import api.repository.DeveloperRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author : NozjkoiTop
 * @Date : Created in 12.02.2022
 */

@CrossOrigin
@RestController
@RequestMapping("/api")
@Api(description = "Api controller")
public class DevController {

    @Autowired
    private DeveloperRepository developerRepository;

    @GetMapping("/developers")
    @ApiOperation("Getting all list of developers")
    public List<Developer> getAllDevelopers() {
        return developerRepository.findAll();
    }

    @PostMapping("/developers")
    @ApiOperation("Creation of a new developer")
    public Developer createDeveloper(@RequestBody Developer developer) throws IllegalAccessException {
        if (!(developerRepository.findDeveloperByEmail(developer.getEmail()).isEmpty())) {
            throw new IllegalArgumentException("This EMail is already exists!!");
        } else if (!(developer.getName().matches("^[a-zA-Z](.*)")) | developer.getName().length() >= 50 | developer.getName().length() < 2) {
            throw new IllegalArgumentException("Incorrect name!!");
        }

        return developerRepository.save(developer);
    }

    @GetMapping("/developers/{id}")
    @ApiOperation("Getting developer by IP")
    public ResponseEntity<Developer> getDeveloperById(@PathVariable long id) {
        Developer developer = developerRepository.findById(id)
                .orElseThrow(() -> new DeveloperNotFoundException("Developer not exist with id:" + id));
        return ResponseEntity.ok(developer);
    }

    @PutMapping("/developers/{id}")
    @ApiOperation("Developer's modifying")
    public ResponseEntity<Developer> updateDeveloper(@PathVariable long id, @RequestBody Developer developerDetails) {
        Developer updateDeveloper = developerRepository.findById(id)
                .orElseThrow(() -> new DeveloperNotFoundException("Developer not exist with id: " + id));
        updateDeveloper.setName(developerDetails.getName());
        updateDeveloper.setEmail(developerDetails.getEmail());
        developerRepository.save(updateDeveloper);
        return ResponseEntity.ok(updateDeveloper);
    }

}