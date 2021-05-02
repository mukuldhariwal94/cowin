package com.example.cowin.model;

import java.util.List;

/**
 * Class to store appointment session
 * <p>
 * * "sessions": [
 * * {
 * * "session_id": "94e6aad2-1d29-476b-b21f-57faf86cbfde",
 * * "date": "12-05-2021",
 * * "available_capacity": 0,
 * * "min_age_limit": 45,
 * * "vaccine": "",
 * * "slots": [
 * * "10:00AM-11:00AM",
 * * "11:00AM-12:00PM",
 * * "12:00PM-01:00PM",
 * * "01:00PM-04:00PM"
 * * ]
 */
public class CowinResponseAppointmentSession {
    private String date;
    private String available_capacity;
    private String min_age_limit;
    private String vaccine;
    private List<String> slots;

    public CowinResponseAppointmentSession() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAvailable_capacity() {
        return available_capacity;
    }

    public void setAvailable_capacity(String available_capacity) {
        this.available_capacity = available_capacity;
    }

    public String getMin_age_limit() {
        return min_age_limit;
    }

    public void setMin_age_limit(String min_age_limit) {
        this.min_age_limit = min_age_limit;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public List<String> getSlots() {
        return slots;
    }

    public void setSlots(List<String> slots) {
        this.slots = slots;
    }

    @Override
    public String toString() {
        return "CowinResponseAppointmentSession{" +
                "date='" + date + '\'' +
                ", available_capacity='" + available_capacity + '\'' +
                ", min_age_limit='" + min_age_limit + '\'' +
                ", vaccine='" + vaccine + '\'' +
                ", slots=" + slots +
                '}';
    }
}
