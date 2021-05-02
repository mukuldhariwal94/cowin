package com.example.cowin.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledAvailabilityChecker.class);
    @Autowired
    private CowinAppointmentCheckerService cowinAppointmentCheckerService;

    @Scheduled(fixedRate = 100000, initialDelay = 0)
    public void scheduledAppointmentChecker()
    {
        LOGGER.info("Starting to check");
        cowinAppointmentCheckerService.getTodayAvailableSlotsAndMail();
    }
}