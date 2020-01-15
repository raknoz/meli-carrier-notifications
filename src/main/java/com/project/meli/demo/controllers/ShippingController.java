package com.project.meli.demo.controllers;


import com.project.meli.demo.dtos.ShippingRequestDTO;
import com.project.meli.demo.dtos.ShippingResponseDTO;
import com.project.meli.demo.entities.ShippingHistoricalRecord;
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
import java.util.List;

@RestController
@RequestMapping
public class ShippingController {
    private ShippingService shippingService;
    private final Logger logger = LoggerFactory.getLogger(ShippingController.class);

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
        return ResponseEntity.ok("I am alive!");
    }

    /**
     * Method to get information about the historical queries of shipping status.
     *
     * @param pageNo   Number of page to consult.
     * @param pageSize How many elements give in the page.
     * @return List of elements.
     */
    @GetMapping("/statistics")
    public ResponseEntity<List<ShippingHistoricalRecord>> getStatisticsByDate(@RequestParam(name = "page", defaultValue = "0") Integer pageNo,
                                                                              @RequestParam(name = "size", defaultValue = "10") Integer pageSize) {

        return new ResponseEntity<>(shippingService.getStatisticsByDate(pageNo, pageSize, null), HttpStatus.OK);
    }


}
