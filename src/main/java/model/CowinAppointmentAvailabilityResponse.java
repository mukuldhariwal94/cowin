package model;

import java.util.List;

/**
 * {
 * "centers": [
 * {
 * "center_id": 631383,
 * "name": "Yelahanka TH COVAXIN",
 * "state_name": "Karnataka",
 * "district_name": "Bangalore Urban",
 * "block_name": "Bengaluru North",
 * "pincode": 560064,
 * "lat": 13,
 * "long": 77,
 * "from": "10:00:00",
 * "to": "16:00:00",
 * "fee_type": "Free",
 * "sessions": [
 * {
 * "session_id": "94e6aad2-1d29-476b-b21f-57faf86cbfde",
 * "date": "12-05-2021",
 * "available_capacity": 0,
 * "min_age_limit": 45,
 * "vaccine": "",
 * "slots": [
 * "10:00AM-11:00AM",
 * "11:00AM-12:00PM",
 * "12:00PM-01:00PM",
 * "01:00PM-04:00PM"
 * ]
 * }
 * ]
 * }
 * ]
 * }
 */
public class CowinAppointmentAvailabilityResponse {
    public CowinAppointmentAvailabilityResponse() {
    }

    public List<CowinResponseCenters> getCenters() {
        return centers;
    }

    public void setCenters(List<CowinResponseCenters> centers) {
        this.centers = centers;
    }

    List<CowinResponseCenters> centers;

}
