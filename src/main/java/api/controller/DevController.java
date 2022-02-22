package api.controller;


import api.dto.DevDataDTO;
import api.dto.DeveloperResponseDTO;
import api.dto.LoginDTO;
import api.model.Developer;
import api.service.DeveloperService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author : NozjkoiTop
 * @Date : Created in 12.02.2022
 */

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Api(tags = "developers")
public class DevController {

    private final DeveloperService developerService;
    private final ModelMapper modelMapper;

    @PostMapping("/auth/login")
    @ApiOperation(value = "${DevController.login}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 422, message = "Invalid username/password supplied")})
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        return developerService.login(loginDTO);
    }

    @PostMapping("/auth/builderJWT")
    @ApiOperation(value = "${JWTTokenGeneration(signup)}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong")})
    public String signup(@ApiParam("Signup Developer") @RequestBody Developer developer) {
        return developerService.signup(modelMapper.map(developer, Developer.class));
    }

    @GetMapping("/developers")
    @PreAuthorize("hasRole('ROLE_USER')or hasRole ('ROLE_HR') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${DevController.findAll}", authorizations = {@Authorization(value = "Authorization")})
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 401, message = "Unauthorized"),//
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")}) //
    public List<Developer> getAllDevelopers() {
        return developerService.findAll();
    }

    @DeleteMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${DevController.delete}", authorizations = {@Authorization(value = "Authorization")})
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 401, message = "Unauthorized"),//
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 404, message = "The user doesn't exist"), //
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public String delete(@ApiParam("Username") @PathVariable String username) {
        developerService.delete(username);
        return username;
    }

    @GetMapping(value = "developers/{username}")
    @PreAuthorize("hasRole('ROLE_USER')or hasRole ('ROLE_HR') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${DevController.search}", response = DeveloperResponseDTO.class, authorizations = {@Authorization(value = "Authorization")})
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 401, message = "Unauthorized"),//
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 404, message = "The user doesn't exist"), //
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public DeveloperResponseDTO search(@ApiParam("Username") @PathVariable String username) {
        return modelMapper.map(developerService.search(username), DeveloperResponseDTO.class);
    }

    @PostMapping(value = "developers/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "{$Developer's modifying}", authorizations = {@Authorization(value = "Authorization")})
    @ApiResponses(value = {//
            @ApiResponse(code = 401, message = "Unauthorized"),//
            @ApiResponse(code = 404, message = "The user doesn't exist"), //
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public ResponseEntity<Developer> updateDeveloper(@PathVariable long id, @RequestBody DevDataDTO devDataDTO) {
        return developerService.updateDeveloper(id, devDataDTO);
    }

}

