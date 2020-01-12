package com.project.meli.demo.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@AutoConfigureMockMvc
public class PackageControllerTest {
    private static final String BASE_URL = "/package";

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }
}
