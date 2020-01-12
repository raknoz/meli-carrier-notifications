package com.project.meli.demo.controllers;


import com.project.meli.demo.dtos.PackageRequestDTO;
import com.project.meli.demo.dtos.PackageResponseDTO;
import com.project.meli.demo.services.PackageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/package")
public class PackageController {
    private PackageService packageService;

    public PackageController(final PackageService packageService) {
        this.packageService = packageService;
    }

    /**
     * Method which process the states of the order and return the message of that state.
     *
     * @param request Information about the order ID and the states of that.
     * @return A Object with the message of state.
     */
    @PostMapping
    public ResponseEntity<PackageResponseDTO> getLastOrderState(@RequestBody final PackageRequestDTO request) {
        return ResponseEntity.ok(new PackageResponseDTO(packageService.packages(request)));
    }

    /**
     * Method to validate the status health of the application.
     *
     * @return String with text in case to validate the status of the application.
     */
    @GetMapping("/health")
    public ResponseEntity<String> getHealth() {
        return ResponseEntity.ok("I am alive!");
    }
}
