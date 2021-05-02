package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties
public class CowinResponseCenters {
    private String name;
    private String state_name;
    private String district_name;
    private String block_name;
    private String pincode;
    private String lat;
    private String Long;
    private String from;
    private String to;
    private String fee_type;
    private String center_id;
    private List<CowinResponseAppointmentSession> sessions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getBlock_name() {
        return block_name;
    }

    public void setBlock_name(String block_name) {
        this.block_name = block_name;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public List<CowinResponseAppointmentSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<CowinResponseAppointmentSession> sessions) {
        this.sessions = sessions;
    }


    public CowinResponseCenters() {
    }

    public String getCenter_id() {
        return center_id;
    }

    public void setCenter_id(String center_id) {
        this.center_id = center_id;
    }

    @Override
    public String toString() {
        return "CowinResponseCenters{" +
                "name='" + name + '\'' +
                ", state_name='" + state_name + '\'' +
                ", district_name='" + district_name + '\'' +
                ", block_name='" + block_name + '\'' +
                ", pincode='" + pincode + '\'' +
                ", lat='" + lat + '\'' +
                ", Long='" + Long + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", fee_type='" + fee_type + '\'' +
                ", center_id='" + center_id + '\'' +
                ", sessions=" + sessions +
                '}';
    }
}
