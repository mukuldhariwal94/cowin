package com.example.cowin;

import com.example.cowin.service.CowinAppointmentCheckerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.cowin.model.ApplicationResponseAvailableSlots;
import com.example.cowin.model.CowinAppointmentAvailabilityResponse;
import com.example.cowin.model.CowinResponseAppointmentSession;
import com.example.cowin.model.CowinResponseCenters;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

class CowinApplicationTests {

    @Test
    public void dateFormattingTest() {
        String ans = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        System.out.println(ans);
    }

    @Test
    public void stringFormattingTest() {
        String APPOINTMENT_ENDPOINT = "appointment/sessions/public/calendarByDistrict?district_id=%s&date=%s";
        // bangalore urban, bbmp, bangalore rural
        List<String> districts = Arrays.asList("265", "294", "276");

        for (String districtId :
                districts) {
            String endPoint = String.format(APPOINTMENT_ENDPOINT, LocalDate.now().toString(), districtId);
            System.out.println(endPoint);
        }
    }

    @Test
    public void mailSenderTest() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.sendgrid.net");
        mailSender.setPort(587);
        mailSender.setUsername("apikey");
        mailSender.setPassword("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");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        mailSender.setJavaMailProperties(props);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mukul.dhariwal94@gmail.com");
        message.setTo("priyal.dhariwal94@gmail.com");
        message.setSubject("test");
        message.setText("text");
        mailSender.send(message);
    }

    /**
     * Example result to get the values.
     */
    @Test
    public void testResponse() throws IOException, MessagingException {
        List<String> results = new ArrayList<>();
        String res1 = "{\"centers\":[{\"center_id\":644158,\"name\":\"COMPOSITE HOSPITAL C R P F\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":560064,\"lat\":13,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"b7f045e0-52d0-4023-83f6-80e3b46a6d8d\",\"date\":\"01-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":656719,\"name\":\"Indalwadi PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Anekal\",\"pincode\":562106,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"2e9dbbe7-b497-469e-b47a-670c68cea9dd\",\"date\":\"01-05-2021\",\"available_capacity\":46,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]},{\"session_id\":\"63c367c6-0d77-4041-872f-fdff21322232\",\"date\":\"02-05-2021\",\"available_capacity\":39,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-12:00PM\",\"12:00PM-01:00PM\"]},{\"session_id\":\"3a442d43-98fa-4f9f-af59-67edf1ce429c\",\"date\":\"03-05-2021\",\"available_capacity\":35,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":662656,\"name\":\"Tippur Subcentre. K Gollahalli\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru South\",\"pincode\":560074,\"lat\":12,\"long\":77,\"from\":\"09:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"a2137e8a-fe79-413a-a0fa-c6dca182e229\",\"date\":\"01-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"09:00AM-11:00AM\",\"11:00AM-01:00PM\",\"01:00PM-03:00PM\",\"03:00PM-04:00PM\"]}]},{\"center_id\":662841,\"name\":\"AVALAHALLI Sub Centre\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru East\",\"pincode\":560036,\"lat\":0,\"long\":0,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"d84acfb3-ecef-45ea-92ef-5fb7b7294adb\",\"date\":\"01-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":647809,\"name\":\"MI Room Air Force Station\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":560090,\"lat\":13,\"long\":77,\"from\":\"09:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"88ddfea1-91e9-4f06-a0e3-665674d8cd49\",\"date\":\"01-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"09:00AM-11:00AM\",\"11:00AM-01:00PM\",\"01:00PM-03:00PM\",\"03:00PM-04:00PM\"]}]},{\"center_id\":586940,\"name\":\"DCHC Leprosy Hospital\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":560023,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"c91dbc11-6a6c-404a-8c1a-606c889ca281\",\"date\":\"01-05-2021\",\"available_capacity\":23,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":678531,\"name\":\"HUSUKURU SC HEBBAGODI\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Anekal\",\"pincode\":560100,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"f8282d4a-09a7-48b6-aa4f-c788e343a648\",\"date\":\"01-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":662916,\"name\":\"Doddabanahalli Sub Centre\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru East\",\"pincode\":560049,\"lat\":0,\"long\":0,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"5a5116e8-8441-4fb2-8ab6-ebff0a4153d3\",\"date\":\"01-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249059,\"name\":\"Mathahalli Sub Centre\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":562123,\"lat\":13,\"long\":77,\"from\":\"10:00:00\",\"to\":\"14:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"6a7d8b12-03a9-4cf5-a563-6683a0d109a6\",\"date\":\"01-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-02:00PM\"]}]},{\"center_id\":249084,\"name\":\"Rajanukunte PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":560064,\"lat\":13,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"3ec440c1-f05b-4f00-97ed-68aba24cbd61\",\"date\":\"01-05-2021\",\"available_capacity\":6,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":692461,\"name\":\"Ragihalli Sub Centre\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Anekal\",\"pincode\":560083,\"lat\":12,\"long\":77,\"from\":\"09:00:00\",\"to\":\"22:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"a3bb2b2a-78da-4433-bbf4-30b2b60c76d8\",\"date\":\"01-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"09:00AM-12:00PM\",\"12:00PM-03:00PM\",\"03:00PM-06:00PM\",\"06:00PM-10:00PM\"]}]},{\"center_id\":663450,\"name\":\"Sonnenahalli Sub Center\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":560089,\"lat\":13,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"72200bea-6548-4f0e-8d6a-07f7c0247cbf\",\"date\":\"01-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249064,\"name\":\"Guddahatti PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Anekal\",\"pincode\":562107,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"525568fe-185c-46c7-9568-8dc6745b3ab0\",\"date\":\"01-05-2021\",\"available_capacity\":43,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]},{\"session_id\":\"c27fa4c9-2c4f-433f-8dd0-7823c71201e1\",\"date\":\"02-05-2021\",\"available_capacity\":43,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-12:00PM\",\"12:00PM-01:00PM\"]},{\"session_id\":\"2d539170-8e16-4ad2-b3bb-608cb299de8a\",\"date\":\"03-05-2021\",\"available_capacity\":16,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":632866,\"name\":\"INST OF AEROSPACE MEDICINE\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":560017,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"6571afdd-413c-4bba-98dd-5ab3786cb75d\",\"date\":\"01-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":671857,\"name\":\"Chikka Hosahalli SC Indlawadi\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Anekal\",\"pincode\":562106,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"895bfb66-6f82-4c60-9ec6-98caea50cf25\",\"date\":\"01-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":662048,\"name\":\"KANNAMANGALA Sub Centre\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru East\",\"pincode\":560067,\"lat\":13,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"7b09cabd-72d1-4fd3-b8b3-8784a9d4b072\",\"date\":\"01-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249086,\"name\":\"Sondekoppa PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":562130,\"lat\":13,\"long\":77,\"from\":\"10:00:00\",\"to\":\"13:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"79adf26a-2c2c-43ed-ad99-4a1563a8d2b3\",\"date\":\"02-05-2021\",\"available_capacity\":47,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-12:00PM\",\"12:00PM-01:00PM\"]},{\"session_id\":\"fc4c072f-af49-4186-a353-08eae03d822f\",\"date\":\"03-05-2021\",\"available_capacity\":50,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":573111,\"name\":\"Makali PHC 1\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":562162,\"lat\":13,\"long\":77,\"from\":\"10:00:00\",\"to\":\"13:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"4e1cae3a-68b4-4e60-afe5-38c89a2cff05\",\"date\":\"02-05-2021\",\"available_capacity\":27,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-12:00PM\",\"12:00PM-01:00PM\"]},{\"session_id\":\"e4cbb3ee-2296-47ea-8caf-2f05f273546d\",\"date\":\"03-05-2021\",\"available_capacity\":6,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249054,\"name\":\"Gopalpura PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":562123,\"lat\":13,\"long\":77,\"from\":\"10:00:00\",\"to\":\"13:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"64a08e81-a64d-4ffe-9013-5a473b429e14\",\"date\":\"02-05-2021\",\"available_capacity\":45,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-12:00PM\",\"12:00PM-01:00PM\"]},{\"session_id\":\"247c9c01-340f-4076-bfe0-65e1b07105ff\",\"date\":\"03-05-2021\",\"available_capacity\":44,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]},{\"session_id\":\"32b05d33-5557-4beb-b9c5-09f1a8220df4\",\"date\":\"04-05-2021\",\"available_capacity\":42,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249083,\"name\":\"Marasuru PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Anekal\",\"pincode\":562106,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"13:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"31ff49f1-7220-46de-a4ba-8ef9ca11af8a\",\"date\":\"02-05-2021\",\"available_capacity\":26,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-12:00PM\",\"12:00PM-01:00PM\"]},{\"session_id\":\"311e1c8a-4f71-4893-8f67-88e96591971e\",\"date\":\"03-05-2021\",\"available_capacity\":16,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249082,\"name\":\"Manduru PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru East\",\"pincode\":560049,\"lat\":13,\"long\":77,\"from\":\"10:00:00\",\"to\":\"13:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"c4fdd7e3-c515-4aa5-8fbe-c098391023ab\",\"date\":\"02-05-2021\",\"available_capacity\":15,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-12:00PM\",\"12:00PM-01:00PM\"]},{\"session_id\":\"e34b97ae-28b1-4970-abb6-a78b4c9d8c2e\",\"date\":\"03-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249046,\"name\":\"Bidarahalli PHCSamudaya Bhavan\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru East\",\"pincode\":560049,\"lat\":13,\"long\":77,\"from\":\"10:00:00\",\"to\":\"13:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"65cfdf41-3fc9-412e-9612-4136b687d84f\",\"date\":\"02-05-2021\",\"available_capacity\":1,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-12:00PM\",\"12:00PM-01:00PM\"]},{\"session_id\":\"9c81a821-8dbb-424e-bba0-6fc3b2e82e63\",\"date\":\"03-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249072,\"name\":\"Kumbalagodu Gollahalli PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru South\",\"pincode\":560074,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"13:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"90021633-f8f4-4182-bb8c-61ed198a98c3\",\"date\":\"02-05-2021\",\"available_capacity\":43,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-12:00PM\",\"12:00PM-01:00PM\"]},{\"session_id\":\"07d90f36-4c10-4d25-967f-fae92ede0ecb\",\"date\":\"03-05-2021\",\"available_capacity\":44,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249031,\"name\":\"Attibele PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Anekal\",\"pincode\":562107,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"13:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"09bd8060-231e-4dc9-b23e-8db0ac728f87\",\"date\":\"02-05-2021\",\"available_capacity\":2,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-12:00PM\",\"12:00PM-01:00PM\"]}]},{\"center_id\":249053,\"name\":\"Dommasandra PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Anekal\",\"pincode\":562125,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"13:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"897b3b06-529b-4c60-8390-c9b73bb5c1ed\",\"date\":\"02-05-2021\",\"available_capacity\":8,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-12:00PM\",\"12:00PM-01:00PM\"]},{\"session_id\":\"e0f3cb1b-7c2a-4ca1-9ee7-4b7dd6c6ebdc\",\"date\":\"03-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":672263,\"name\":\"MADHANAYAKANAHALLI SC MAKLI\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":562123,\"lat\":13,\"long\":77,\"from\":\"10:00:00\",\"to\":\"13:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"09baad94-0ff3-44a4-b5d5-975d44c907bf\",\"date\":\"02-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-12:00PM\",\"12:00PM-01:00PM\"]}]},{\"center_id\":595144,\"name\":\"Central Prison Hospital\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Anekal\",\"pincode\":560099,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"34f933a0-8cbf-43ce-a62e-e7f197f99cb3\",\"date\":\"03-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249042,\"name\":\"Balluru PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Anekal\",\"pincode\":562107,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"de785d77-1d85-418a-b94a-6254d77846d3\",\"date\":\"03-05-2021\",\"available_capacity\":32,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249096,\"name\":\"Sulikere PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru South\",\"pincode\":560060,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"c4860857-26d9-4cd1-a08a-f0042ee2866c\",\"date\":\"03-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":631383,\"name\":\"Yelahanka TH COVAXIN\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":560064,\"lat\":13,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"a6ca48e2-12c5-4ba1-a9f1-5a2bd93b4c5c\",\"date\":\"03-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249152,\"name\":\"Bettahalsuru PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":562157,\"lat\":13,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"a6017867-8232-4c76-8f12-2e27ce5ba154\",\"date\":\"03-05-2021\",\"available_capacity\":26,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249048,\"name\":\"Bolare PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru South\",\"pincode\":560116,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"d0896408-3aee-4779-9838-5dff895d884c\",\"date\":\"03-05-2021\",\"available_capacity\":41,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249041,\"name\":\"Bagaluru PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":562149,\"lat\":13,\"long\":77,\"from\":\"15:00:00\",\"to\":\"17:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"521044c1-b93f-4c26-ad92-9a97f9fad137\",\"date\":\"03-05-2021\",\"available_capacity\":19,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"03:00PM-05:00PM\"]}]},{\"center_id\":249085,\"name\":\"Sarjapura PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Anekal\",\"pincode\":562125,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"f6087979-37eb-4af8-8bb4-6ca1c410ba6d\",\"date\":\"03-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":519615,\"name\":\"Kadusonnappanahalli PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru East\",\"pincode\":562149,\"lat\":13,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"6572a762-30aa-45b8-a7eb-4f1b2018f3a0\",\"date\":\"03-05-2021\",\"available_capacity\":44,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249097,\"name\":\"Tavarekere PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru South\",\"pincode\":562130,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"4da2f988-6b74-4d9c-875c-e1f463261b04\",\"date\":\"03-05-2021\",\"available_capacity\":11,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249077,\"name\":\"General Hospital K R Puram\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru East\",\"pincode\":560036,\"lat\":13,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"d1869540-2c18-469e-a093-434ef2b71a0d\",\"date\":\"03-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249065,\"name\":\"Gunjuru PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru East\",\"pincode\":560087,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"ddb4a7f4-90fe-46aa-98e6-1bd13f50e6f5\",\"date\":\"03-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249087,\"name\":\"Sonnenahalli PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":560089,\"lat\":13,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"521ad705-d15c-4840-a290-b3fa9a5cbed0\",\"date\":\"03-05-2021\",\"available_capacity\":35,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":632833,\"name\":\"ASC CENTRE\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":560007,\"lat\":12,\"long\":77,\"from\":\"09:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"6da390a0-142e-488c-b75e-2fc35cd5c81c\",\"date\":\"03-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"09:00AM-11:00AM\",\"11:00AM-01:00PM\",\"01:00PM-03:00PM\",\"03:00PM-04:00PM\"]}]},{\"center_id\":249040,\"name\":\"Avalahalli CHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru East\",\"pincode\":560049,\"lat\":13,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"18e6619d-9f2c-4ea7-ba51-b8044163a879\",\"date\":\"03-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249066,\"name\":\"Halanayakanahalli PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru East\",\"pincode\":560035,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"a1254a4a-1334-4571-8f99-9db896b5da7f\",\"date\":\"03-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249071,\"name\":\"Jigani PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Anekal\",\"pincode\":560105,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"c1512fb1-acf5-42aa-8040-e3632814068d\",\"date\":\"03-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249075,\"name\":\"Kannuru PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru East\",\"pincode\":562149,\"lat\":13,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"a9d7bf33-5ed7-4474-bdc0-68b18ade8036\",\"date\":\"03-05-2021\",\"available_capacity\":25,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249079,\"name\":\"Machohalli PHC Valmiki Bhavana\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":560091,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"4dc0b45d-45ee-418e-aa09-57df90fab16f\",\"date\":\"03-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249067,\"name\":\"Haragadde PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Anekal\",\"pincode\":560105,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"ddb4d724-13c7-438c-96d8-bce84c80b7fb\",\"date\":\"03-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249051,\"name\":\"Chikkabanavara PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":560090,\"lat\":13,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"5354e643-1db2-48c8-9b95-91c01725c648\",\"date\":\"03-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]},{\"session_id\":\"9046199b-5178-4b3d-b42b-9da53049b14f\",\"date\":\"04-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249050,\"name\":\"Chandrappa Circle CHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru South\",\"pincode\":562130,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"96e2079b-b2bf-418e-bc1e-ac276b48188c\",\"date\":\"03-05-2021\",\"available_capacity\":40,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":590616,\"name\":\"Yelahanka TH.\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":562157,\"lat\":13,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"6a796036-3fa2-4ad1-9d3a-c639f04bd5a5\",\"date\":\"03-05-2021\",\"available_capacity\":8,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249052,\"name\":\"Chikkajala PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":562157,\"lat\":13,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"14daae94-eb79-4f41-be0f-0c671bf7c8c8\",\"date\":\"03-05-2021\",\"available_capacity\":24,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249074,\"name\":\"Kannalli PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":560112,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"29b2e732-0c8f-4dbb-afb9-8005934153df\",\"date\":\"03-05-2021\",\"available_capacity\":33,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249073,\"name\":\"Kaggalipura CHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru South\",\"pincode\":560116,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"11eadd3d-4523-4d3d-98b8-f0aae98da8b6\",\"date\":\"03-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249078,\"name\":\"Kumbalgodu PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru South\",\"pincode\":560074,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"10abb905-d53b-44ab-a476-972540e3903c\",\"date\":\"03-05-2021\",\"available_capacity\":24,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249049,\"name\":\"Chandapura PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Anekal\",\"pincode\":560099,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"d18f0edd-da59-491a-ac0e-9349c7307e7e\",\"date\":\"03-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":249076,\"name\":\"KODATHI PHC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru East\",\"pincode\":560035,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"bf7fd949-53b0-4603-8d92-afa083b5dfe3\",\"date\":\"03-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]},{\"center_id\":673892,\"name\":\"ECHS POLY CLINIC\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Bengaluru North\",\"pincode\":560007,\"lat\":12,\"long\":77,\"from\":\"09:00:00\",\"to\":\"13:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"4b6cea16-50a9-4f97-b375-d7e8175aecb0\",\"date\":\"03-05-2021\",\"available_capacity\":46,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"09:00AM-10:00AM\",\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\"]}]},{\"center_id\":675110,\"name\":\"Ramasagara SC Chandapura\",\"state_name\":\"Karnataka\",\"district_name\":\"Bangalore Urban\",\"block_name\":\"Anekal\",\"pincode\":560099,\"lat\":12,\"long\":77,\"from\":\"10:00:00\",\"to\":\"16:00:00\",\"fee_type\":\"Free\",\"sessions\":[{\"session_id\":\"a06cd18f-9dd0-4383-8420-28c739200e23\",\"date\":\"07-05-2021\",\"available_capacity\":0,\"min_age_limit\":45,\"vaccine\":\"\",\"slots\":[\"10:00AM-11:00AM\",\"11:00AM-12:00PM\",\"12:00PM-01:00PM\",\"01:00PM-04:00PM\"]}]}]}";

        String res2 = new String(Files.readAllBytes(Paths.get("src/main/resources/res2.txt")));
        String res3 = new String(Files.readAllBytes(Paths.get("src/main/resources/res3.txt")));

        results.add(res1);
        results.add(res2);
        results.add(res3);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<CowinResponseCenters> filteredCenters = new ArrayList<>();

        for (String result : results) {
            CowinAppointmentAvailabilityResponse response = mapper.readValue(result, CowinAppointmentAvailabilityResponse.class);
            ApplicationResponseAvailableSlots applicationResponseAvailableSlots = new ApplicationResponseAvailableSlots();
            for (CowinResponseCenters center : response.getCenters()) {
                if (!center.getSessions().isEmpty()) {
                    CowinResponseAppointmentSession cowinResponseAppointmentSession = center.getSessions().get(0);
                    Integer age_limit = Integer.parseInt(cowinResponseAppointmentSession.getMin_age_limit());
                    if (age_limit < 45) {
                        filteredCenters.add(center);
                    }
                }
            }

            applicationResponseAvailableSlots.setCenters(filteredCenters);
            System.out.println(applicationResponseAvailableSlots);
        }

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.sendgrid.net");
        mailSender.setPort(587);
        mailSender.setUsername("apikey");
        mailSender.setPassword("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");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        mailSender.setJavaMailProperties(props);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = getHTMLMessage(filteredCenters);
        //mimeMessage.setContent(htmlMsg, "text/html"); /** Use this or below line **/
        helper.setText(htmlMsg, true); // Use this or above line.
        helper.setTo(new String[]{"priyal.dhariwal94@gmail.com", "ashishdhariwal95@gmail.com", "yashsankhlecha7@gmail.com", "kshantanu93@gmail.com"
        ,"suhaib18@gmail.com"});
        helper.setSubject("This is the test message for testing cowin vaccine email tracker");
        helper.setFrom("mukul.dhariwal94@gmail.com");
        mailSender.send(mimeMessage);
    }

    private String getHTMLMessage(List<CowinResponseCenters> filteredCenters) {
        StringBuilder sb = new StringBuilder();

        sb.append("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Vaccination details</title>\n" +
                "    <style>\n" +
                "table {\n" +
                "  width:100%;\n" +
                "}\n" +
                "table, th, td {\n" +
                "  border: 1px solid black;\n" +
                "  border-collapse: collapse;\n" +
                "}\n" +
                "th, td {\n" +
                "  padding: 15px;\n" +
                "  text-align: left;\n" +
                "}\n" +
                "#t01 tr:nth-child(even) {\n" +
                "  background-color: #eee;\n" +
                "}\n" +
                "#t01 tr:nth-child(odd) {\n" +
                " background-color: #fff;\n" +
                "}\n" +
                "#t01 th {\n" +
                "  background-color: black;\n" +
                "  color: white;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table style=\"width:100%\" id=\"t01\">" +
                "<th>Vaccination Center</th>\n" +
                "        <th>Slots Available</th>\n" +
                "        <th>District Under</th>\n" +
                "        <th>Pincode</th>");
        for (int i = 0; i < filteredCenters.size(); i++) {
            sb.append("<tr><td>")
                    .append(filteredCenters.get(i).getName())
                    .append("</td><td>")
                    .append(getInnerTable(filteredCenters.get(i).getSessions()))
                    .append("</td><td>")
                    .append(filteredCenters.get(i).getDistrict_name())
                    .append("</td><td>")
                    .append(filteredCenters.get(i).getPincode())
                    .append("</td></tr>");
        }

        sb.append("</table>" +
                "</body>" +
                "</html>");
        String html = sb.toString();
        return html;
    }

    private String getInnerTable(List<CowinResponseAppointmentSession> sessions) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table style=\"width:100%\" id=\"t01\">" +
                "<th>Date</th>\n" +
                "        <th>Available Capacity</th>\n" +
                "        <th>Slots Available</th>\n" +
                "        <th>Minimum age</th>\n" +
                "        <th>Vaccine</th>");
        for(int i = 0; i < sessions.size(); i++)
        {
            sb.append("<tr><td>")
                    .append(sessions.get(i).getDate())
                    .append("</td><td>")
                    .append(sessions.get(i).getAvailable_capacity())
                    .append("</td><td>")
                    .append(sessions.get(i).getSlots())
                    .append("</td><td>")
                    .append(sessions.get(i).getMin_age_limit())
                    .append("</td><td>")
                    .append(sessions.get(i).getVaccine())
                    .append("</td></tr>");
        }

        sb.append("</table>");
        return sb.toString();
    }
}
