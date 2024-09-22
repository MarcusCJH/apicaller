package com.example.apicaller.controller;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/stress")
@XRayEnabled
public class StressController {

    private static final Logger logger = LoggerFactory.getLogger(StressController.class);

    @Autowired
    private RestTemplate restTemplate;

    // Replace this with your actual CRUD demo API URL
    private final String crudDemoApiUrl = "http://13.229.129.5:8080/api";

    // Call the CPU crash endpoint in the crud-demo API
    @GetMapping("/cpu-crash")
    public ResponseEntity<String> simulateCpuCrash() {
        logger.info("Calling CRUD demo API to simulate CPU crash");
        String url = crudDemoApiUrl + "/cpu-crash";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return ResponseEntity.ok(response.getBody());
    }

    // Call the Memory crash endpoint in the crud-demo API
    @GetMapping("/memory-crash")
    public ResponseEntity<String> simulateMemoryCrash() {
        logger.info("Calling CRUD demo API to simulate Memory crash");
        String url = crudDemoApiUrl + "/memory-crash";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return ResponseEntity.ok(response.getBody());
    }

    // Call the Combined CPU and Memory crash endpoint in the crud-demo API
    @GetMapping("/combined-crash")
    public ResponseEntity<String> simulateCombinedCrash() {
        logger.info("Calling CRUD demo API to simulate Combined crash");
        String url = crudDemoApiUrl + "/combined-crash";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return ResponseEntity.ok(response.getBody());
    }

    // Call the Latency drag endpoint in the crud-demo API
    @GetMapping("/latency-drag")
    public ResponseEntity<String> simulateLatencyDrag() {
        logger.info("Calling CRUD demo API to simulate Latency drag");
        String url = crudDemoApiUrl + "/latency-drag";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return ResponseEntity.ok(response.getBody());
    }

    // Helper method to handle HTTP 5xx errors gracefully
    private ResponseEntity<String> handle5xxErrors(String url, String description) {
        try {
            return restTemplate.getForEntity(url, String.class);
        } catch (HttpServerErrorException ex) {
            // Log the 5xx error and return the actual response status and body
            logger.error("{} returned status code {} with body: {}", description, ex.getStatusCode(), ex.getResponseBodyAsString());
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        } catch (Exception ex) {
            logger.error("Unexpected error occurred while calling {}: {}", description, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
    }

    // Call the 500 Internal Server Error endpoint in the crud-demo API
    @GetMapping("/simulate-500")
    public ResponseEntity<String> simulate500Error() {
        logger.info("Calling CRUD demo API to simulate 500 error");
        String url = crudDemoApiUrl + "/simulate-500";
        return handle5xxErrors(url, "Simulate 500 Error");
    }

    // Call the 502 Bad Gateway error endpoint in the crud-demo API
    @GetMapping("/simulate-502")
    public ResponseEntity<String> simulate502Error() {
        logger.info("Calling CRUD demo API to simulate 502 error");
        String url = crudDemoApiUrl + "/simulate-502";
        return handle5xxErrors(url, "Simulate 502 Error");
    }

    // Call the 504 Gateway Timeout error endpoint in the crud-demo API
    @GetMapping("/simulate-504")
    public ResponseEntity<String> simulate504Error() {
        logger.info("Calling CRUD demo API to simulate 504 error");
        String url = crudDemoApiUrl + "/simulate-504";
        return handle5xxErrors(url, "Simulate 504 Error");
    }
}
