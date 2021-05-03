package com.example.cowin.service;

import com.example.cowin.mail.sender.EmailAlerter;
import com.example.cowin.model.ApplicationResponseAvailableSlots;
import com.example.cowin.model.CowinResponseAppointmentSession;
import com.example.cowin.model.CowinResponseCenters;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.cowin.model.CowinAppointmentAvailabilityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
/**
 * Appointment service checker.
 *
 * @author mukul dhariwal
 */
public class CowinAppointmentCheckerService {

    private static final String COWIN_API_ENDPOINT = "https://cdn-api.co-vin.in/api/v2/";
    private String APPOINTMENT_ENDPOINT = "appointment/sessions/public/calendarByDistrict?district_id=%s&date=%s";
    private static final Logger LOGGER = LoggerFactory.getLogger(CowinAppointmentCheckerService.class);
    // bangalore urban, bbmp, bangalore rural
    private static final List<String> districtsToCheck = Arrays.asList("265", "294", "276");
    @Autowired
    private EmailAlerter emailAlerter;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper mapper;

    @Autowired

    public ApplicationResponseAvailableSlots getTodayAvailableSlotsAndMail() {
        ApplicationResponseAvailableSlots applicationResponseAvailableSlots = new ApplicationResponseAvailableSlots();
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        List<CowinResponseCenters> filteredCenters = new ArrayList<>();
        try {
            for (String district : districtsToCheck) {
                String finalEndpoint = COWIN_API_ENDPOINT + String.format(APPOINTMENT_ENDPOINT, district, today);
                LOGGER.info("Hitting endpoint {}", finalEndpoint);
                String result = restTemplate.getForObject(finalEndpoint, String.class);
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                CowinAppointmentAvailabilityResponse response = mapper.readValue(result, CowinAppointmentAvailabilityResponse.class);
                for (CowinResponseCenters center : response.getCenters()) {
                    if (!center.getSessions().isEmpty()) {
                        for (CowinResponseAppointmentSession cowinResponseAppointmentSession : center.getSessions()) {
                            Integer age_limit = Integer.parseInt(cowinResponseAppointmentSession.getMin_age_limit());
                            Double available_capacity = Double.parseDouble(cowinResponseAppointmentSession.getAvailable_capacity());
                            if (age_limit < 45 && available_capacity > 0) {
                                filteredCenters.add(center);
                            }
                        }
                    }
                }
            }

            if (!filteredCenters.isEmpty()) {
                emailAlerter.sendEmail(filteredCenters);
            } else {
                LOGGER.info("No centers are giving vaccine right now");
            }
            applicationResponseAvailableSlots.setCenters(filteredCenters);
            return applicationResponseAvailableSlots;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error while getting data");
        }

        return null;
    }
}
