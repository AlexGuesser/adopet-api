package br.com.alura.adopet.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final String EMAIL_SENDER = "adopet@email.com.br";

    @Autowired
    private JavaMailSender emailSender;

    public void enviar(String destinatario, String assunto, String mensagem) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(EMAIL_SENDER);
        email.setTo(destinatario);
        email.setSubject(assunto);
        email.setText(mensagem);
        emailSender.send(email);

    }

}
