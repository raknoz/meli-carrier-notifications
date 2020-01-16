package com.project.meli.demo.controllers;


import com.project.meli.demo.dtos.ShippingRequestDTO;
import com.project.meli.demo.dtos.ShippingResponseDTO;
import com.project.meli.demo.services.ShippingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping
public class ShippingController {
    private static final String HEALTH_MESSAGE = "I'm alive!";
    private final Logger logger = LoggerFactory.getLogger(ShippingController.class);
    private ShippingService shippingService;

    public ShippingController(final ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    /**
     * Method which process the states of the order and return the message of that state.
     *
     * @param request Information about the order ID and the states of that.
     * @return A Object with the message of state.
     */
    @PostMapping("/package")
    public ResponseEntity<ShippingResponseDTO> getLastShippingStatus(@RequestBody @Valid final ShippingRequestDTO request) {
        logger.info(String.format("Validate the status for shipping: %s", request.getId()));
        return ResponseEntity.ok(new ShippingResponseDTO(shippingService.packages(request)));
    }

    /**
     * Method to validate the status health of the application.
     *
     * @return String with text in case to validate the status of the application.
     */
    @GetMapping("/health")
    public ResponseEntity<String> getHealth() {
        return ResponseEntity.ok(HEALTH_MESSAGE);
    }

    /**
     * Method to get information about the historical queries of shipping status.d
     *
     * @param dateFrom Initial date to filter the report.
     * @param dateTo   Final date to filter the report.
     * @return Object with information about the queries to application.
     */
    @GetMapping("/statistics")
    public ResponseEntity<Void> getStatisticsByDate(@RequestParam("dateFrom") final String dateFrom,
                                                    @RequestParam("dateTo")  final String dateTo) {
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
