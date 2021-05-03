package com.example.cowin.config;


import com.example.cowin.mail.sender.EmailAlerter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@Configuration
@EnableScheduling
/**
 * Configuration class for beans.
 *
 * @author mukul dhariwal
 */
public class SpringConfig {

    @Bean
    public JavaMailSender getJavaMailSender() {
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

        return mailSender;
    }

    @Bean
    public EmailAlerter getEmailAlerter() {
        return new EmailAlerter();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}