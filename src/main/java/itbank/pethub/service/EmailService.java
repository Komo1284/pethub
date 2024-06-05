package itbank.pethub.service;

import itbank.pethub.vo.ContactForm;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    public void sendSimpleMessage(ContactForm contactForm) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("rkdgur96@gmail.com");
        message.setSubject("[Pethub] 새로운 문의 메세지");
        message.setText("성함: " + contactForm.getName() + "\n이메일: " + contactForm.getEmail() + "\n남기신 내용: " + contactForm.getMessage());
        emailSender.send(message);
    }

    public void sendAuthNum(String email, String random) throws MessagingException {

        String htmlContent = "<html>" +
                "<body>" +
                "<h1 style='color: orange;'>인증번호 발송</h1>" +
                "<p>인증번호는 <b>" + random + "</b> 입니다.</p>" +
                "</body>" +
                "</html>";

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("[Pethub] 인증번호");
        helper.setText(htmlContent, true);

        emailSender.send(message);
    }
}