package itbank.pethub.service;

import itbank.pethub.vo.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(ContactForm contactForm) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("rkdgur96@gmail.com");
        message.setSubject("[Pethub] 새로운 문의 메세지");
        message.setText("성함: " + contactForm.getName() + "\n이메일: " + contactForm.getEmail() + "\n남기신 내용: " + contactForm.getMessage());
        emailSender.send(message);
    }
}