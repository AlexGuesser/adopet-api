package br.com.alura.adopet.api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    void enviar_deveEnviarEmailComOsParametrosCorretos() {
        ArgumentCaptor<SimpleMailMessage> simpleMailMessageArgumentCaptor =
                ArgumentCaptor.forClass(SimpleMailMessage.class);

        emailService.enviar("destinatario", "assunto", "mensagem");

        then(emailSender).should().send(simpleMailMessageArgumentCaptor.capture());
        SimpleMailMessage message = simpleMailMessageArgumentCaptor.getValue();
        assertThat(message.getTo()[0]).isEqualTo("destinatario");
        assertThat(message.getSubject()).isEqualTo("assunto");
        assertThat(message.getText()).isEqualTo("mensagem");
    }

}