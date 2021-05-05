package com.example.cowin.scheduled;

import com.example.cowin.mail.sender.EmailAlerter;
import com.example.cowin.model.ApplicationResponseAvailableSlots;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.example.cowin.service.CowinAppointmentCheckerService;

/**
 * Scheduled checker runs every hour till end of day
 *
 * @author mukul dhariwal
 */
@Component
public class ScheduledAvailabilityChecker {

    private static final int FIVE_MINUTES = 1000 * 60 * 5;
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledAvailabilityChecker.class);
    @Autowired
    private CowinAppointmentCheckerService cowinAppointmentCheckerService;
    @Autowired
    private EmailAlerter emailAlerter;

    @Scheduled(fixedRate = FIVE_MINUTES, initialDelay = 10000)
    public void scheduledAppointmentChecker() {
        LOGGER.info("Starting to check");
        ApplicationResponseAvailableSlots todayAvailableSlotsAndMail = cowinAppointmentCheckerService.getTodayAvailableSlotsAndMail();
    }
}