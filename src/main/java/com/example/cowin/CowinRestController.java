package com.example.cowin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.cowin.model.ApplicationResponseAvailableSlots;
import com.example.cowin.model.CowinAppointmentAvailabilityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Rest Controller class to fetch values from the COWIN API
 *
 * @author mukul dhariwal
 */
@RestController
public class CowinRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CowinRestController.class);
    private static final String COWIN_API_ENDPOINT = "https://cdn-api.co-vin.in/api/v2/";
    private static final String APPOINTMENT_ENDPOINT = "appointment/sessions/public/calendarByDistrict?district_id=265&date=";
    @GetMapping("getAvailableSlots")
    public ResponseEntity<ApplicationResponseAvailableSlots> getAvailableSlots() throws JsonProcessingException {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(COWIN_API_ENDPOINT + APPOINTMENT_ENDPOINT + today, String.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CowinAppointmentAvailabilityResponse response = mapper.readValue(result, CowinAppointmentAvailabilityResponse.class);

        return null;
    }

}
