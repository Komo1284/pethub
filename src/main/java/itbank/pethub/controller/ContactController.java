package itbank.pethub.controller;

import itbank.pethub.service.EmailService;
import itbank.pethub.vo.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/contact")
    public String submitContactForm(ContactForm contactForm) {
        emailService.sendSimpleMessage(contactForm);
        return "redirect:/board/help";
    }
}