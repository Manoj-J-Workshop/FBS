package com.fbs.notification_api.service;

import com.fbs.notification_api.dto.AirlineRejectDto;
import com.fbs.notification_api.model.Airline;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Slf4j
public class AirlineNotificationService {

    JavaMailSender javaMailSender;
    TemplateEngine templateEngine;

    @Autowired
    public AirlineNotificationService(JavaMailSender javaMailSender, TemplateEngine templateEngine){
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void airlineacceptNotification(Airline airline){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try{
            mimeMessageHelper.setTo(airline.getAdmin().getEmail());
            mimeMessageHelper.setSubject("Airline Registration Request Accepted");
            Context context = new Context();
            context.setVariable("adminName",airline.getAdmin().getName());
            context.setVariable("airlineName", airline.getAirlineName());
            context.setVariable("status", airline.getStatus());
            context.setVariable("createdAt", airline.getCreatedAt().toString());

            String HtmlContent = templateEngine.process("accept-airline-registration", context);
            mimeMessageHelper.setText(HtmlContent,true);
            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            log.error(e.getMessage());
        }


    }

    public void airlineRejectNotification(AirlineRejectDto airlineRejectDto){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try{
            mimeMessageHelper.setTo(airlineRejectDto.getGetAirlineAdminEmail());
            mimeMessageHelper.setSubject("Airline Registration Request Rejected");
            Context context = new Context();
            context.setVariable("airlineAdminName",airlineRejectDto.getAirlineAdminName());
            context.setVariable("airlineName", airlineRejectDto.getOfficialName());
            context.setVariable("aiGeneratedReason", airlineRejectDto.getRejectReason());


            String HtmlContent = templateEngine.process("reject-airline-registration-request", context);
            mimeMessageHelper.setText(HtmlContent,true);
            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
