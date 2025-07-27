package com.fbs.notification_api.service;

import com.fbs.notification_api.dto.AirlineRegistrationReqDto;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Slf4j
public class AppAdminNotificationService {

    JavaMailSender javaMailSender;
    TemplateEngine templateEngine;

    @Autowired
    public AppAdminNotificationService(JavaMailSender javaMailSender, TemplateEngine templateEngine){
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendAirlineRegistrationReqNotification(AirlineRegistrationReqDto airlineRegistrationReqDto){
        //To send mail we require Java Mail Sender library object
        // To send mail we need to set mail content
        // To set content there is a class - MimeMessage, so we will set all the mail content into this class object
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        // To set the values in mimeMailMessage - we need mimeMailMessage helper class object
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage);
        // Now by using mimeMessageHelper class object we will be setting all the mail content
        Context context = new Context(); // this class object help us to set values of the variables present inside the html template
        context.setVariable("airlineName", airlineRegistrationReqDto.getAirline().getAirlineName());
        context.setVariable("officialName", airlineRegistrationReqDto.getAirline().getOfficialName());
        context.setVariable("website", airlineRegistrationReqDto.getAirline().getWebsite());
        context.setVariable("phoneNumber", airlineRegistrationReqDto.getAirline().getPhoneNumber());
        context.setVariable("employees", airlineRegistrationReqDto.getAirline().getEmployees());
        context.setVariable("totalFlights", airlineRegistrationReqDto.getAirline().getTotalFlights());

//        if (airlineRegistrationReqDto.getAirline().getCreatedAt() != null) {
//            context.setVariable("createdAt", airlineRegistrationReqDto.getAirline().getCreatedAt().toString());
//        } else {
//            context.setVariable("createdAt", "N/A");
//            log.warn("Airline createdAt is null");
//        }

        context.setVariable("createdAt", airlineRegistrationReqDto.getAirline().getCreatedAt().toString());
        context.setVariable("AirlineAdminName", airlineRegistrationReqDto.getAirline().getAdmin().getName());
        context.setVariable("AirlineAdminEmail", airlineRegistrationReqDto.getAirline().getAdmin().getEmail());
        context.setVariable("AirlineAdminPhone", airlineRegistrationReqDto.getAirline().getAdmin().getPhoneNumber());
        context.setVariable("acceptLink", "http://localhost:8081/api/v1/central/airline/request/accept/" + airlineRegistrationReqDto.getAirline().getId().toString());
        //context.setVariable("rejectLink", "http://localhost:8081/api/v1/central/airline/request/request/" + airlineRegistrationReqDto.getAirline().getId().toString());
        context.setVariable("rejectLink", "http://localhost:8081/api/v1/central/airline/request/reject/" + airlineRegistrationReqDto.getAirline().getId().toString());


        //context.setVariable("acceptLink", "http://localhost:8081/api/v1/airline/request/accept/" + airlineRegistrationReqDto.getAirline().getId().toString());

        // we need to load the html template inside this function and populate the values of all the variables
        // To do it - we will use library called Thymeleaf.
        // we need object of TemplateEngine (present inside Thymeleaf) - get it from springboot, so we need to create bean of Thymeleaf class and store it inside a IOC container
        String htmlContent = templateEngine.process("airline-registration-request",context); // this method load the template inside our java function

        try{
            mimeMessageHelper.setTo(airlineRegistrationReqDto.getAppAdmin().getEmail());// By using this method we will be setting the mailID we will set for whom mail need to be sent
            mimeMessageHelper.setSubject(airlineRegistrationReqDto.getAirline().getAirlineName()+" Registration Request");
            //mimeMessageHelper.setText("Hey, There is the new registration Request"); -> this will send normal text email
            mimeMessageHelper.setText(htmlContent, true); // when we need to send html content in this setText(), we need to pass another boolean parameter
            //if we are passing boolean parameter as true means passing html content
            //true / false - the content is HTML or plain text.

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        //By calling below method we will be sending email to the users.
        javaMailSender.send(mimeMailMessage);


    }
}
