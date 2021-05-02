package com.example.cowin.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.cowin.model.CowinAppointmentAvailabilityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CowinAppointmentCheckerService {

    private static final String COWIN_API_ENDPOINT = "https://cdn-api.co-vin.in/api/v2/";
    private static final String APPOINTMENT_ENDPOINT = "appointment/sessions/public/calendarByDistrict?district_id=265&date=";
    private static final Logger LOGGER = LoggerFactory.getLogger(CowinAppointmentCheckerService.class);


    public void getTodayAvailableSlotsAndMail()
    {
        try {
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(COWIN_API_ENDPOINT + APPOINTMENT_ENDPOINT + today, String.class);
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            CowinAppointmentAvailabilityResponse response = mapper.readValue(result, CowinAppointmentAvailabilityResponse.class);
            System.out.println(response.toString());
        }
        catch(Exception e)
        {
            LOGGER.error(e.getMessage());
        }
    }
}
