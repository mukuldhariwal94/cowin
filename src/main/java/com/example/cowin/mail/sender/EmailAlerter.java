package com.example.cowin.mail.sender;

import com.example.cowin.model.CowinResponseAppointmentSession;
import com.example.cowin.model.CowinResponseCenters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.List;

/**
 * Email alerter to send email alert when a slot is available
 *
 * @author mukuldhariwal
 */
public class EmailAlerter {

    @Autowired
    private JavaMailSender javaMailSender;
    private static final String[] recipients = new String[]{"ashishdhariwal95@gmail.com",
            "yashsankhlecha7@gmail.com","mukul.dhariwal94@gmail.com"};

    public void sendEmail(List<CowinResponseCenters> filteredCenters) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = getHTMLMessage(filteredCenters);
        helper.setText(htmlMsg, true);
        helper.setTo(recipients);
        helper.setSubject("Vaccine availability checker");
        helper.setFrom("mukul.dhariwal94@gmail.com");
        javaMailSender.send(mimeMessage);
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
        for (int i = 0; i < sessions.size(); i++) {
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