package com.fbs.notification_api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.Thymeleaf;

import java.util.HashMap;
import java.util.Properties;

@Configuration
public class AppConfiguration {

    /*
    @Bean - will ask the springboot to save the hashmap  object inside the IOC container
    And whenever someone will try to autowire hashmap object springboot will provide that object from IOC container
     */
    @Bean
    public HashMap<Integer, Integer> generateHashMap(){
        return new HashMap<>();
    }
    /*
    JavaMailSender - helps springboot code to send mails to mail IDs.
     */
    @Bean
    public JavaMailSender generateJavaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        //Our backend API needs to send mails for the user
        //Need to provide credentials of the emailID by using which back-end will send mail to users of the Application
        javaMailSender.setHost("smtp.gmail.com");// For now email using belongs to gmail, so the host will be smtp.gmail.com
        javaMailSender.setPort(587);// generally to send mail from our computer we require some port number, so the port number we are using 587
        javaMailSender.setUsername("accioshoppingwebsite@gmail.com");// We will be sending email so, by what email our spring application will send mail to the users
        javaMailSender.setPassword("relcfdwhahhcvokv");// password of the email...... It is app password, not actual password

        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.smtp.auth","true"); // springboot api connect with gmail to send email via password so, mail.smtp.auth is true
        props.put("mail.smtp.starttls.enable","true");// this property we are setting for secure connection
        return javaMailSender;
    }

    /*
    Below method will create a bean of TemplateEngine class (Present inside Thymeleaf) and store in IOC container
     */
    @Bean
    public TemplateEngine getThymeleafBean(){
        return new TemplateEngine();

    }
}
