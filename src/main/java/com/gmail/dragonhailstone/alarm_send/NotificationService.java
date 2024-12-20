package com.gmail.dragonhailstone.alarm_send;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationService {

    @KafkaListener(topics = "price-match", groupId = "notification")
    public void sendNotification(String message) {
        System.out.println("Matched message: " + message);
        String[] parts = message.split(", ");
        String username = parts[0].split(": ")[1];
        String coin = parts[1].split(": ")[1];

        // Twilio를 통한 SMS 발송
        sendSms(username, coin);
        sendAws(username, coin);
    }

    private void sendAws(String username, String coin) {

    }

    private void sendSms(String username, String coin) {
        Twilio.init("your_twilio_account_sid", "your_twilio_auth_token");
        Message.creator(
                new PhoneNumber("user_phone_number"), // 대상 번호
                new PhoneNumber("your_twilio_phone_number"), // Twilio 번호
                "Hello " + username + ", the price for " + coin + " has been reached!"
        ).create();
    }
}
