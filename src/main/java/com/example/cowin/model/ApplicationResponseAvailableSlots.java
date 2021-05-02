package com.example.cowin.model;

import java.util.List;

/**
 * Model class for response on availble slots for vaccine
 *
 * @author mukuldhariwal
 */
public class ApplicationResponseAvailableSlots {
    List<CowinResponseCenters> centers;

    @Override
    public String toString() {
        return "ApplicationResponseAvailableSlots{" +
                "centers=" + centers +
                '}';
    }

    public List<CowinResponseCenters> getCenters() {
        return centers;
    }

    public ApplicationResponseAvailableSlots() {
    }

    public void setCenters(List<CowinResponseCenters> centers) {
        this.centers = centers;
    }

}
