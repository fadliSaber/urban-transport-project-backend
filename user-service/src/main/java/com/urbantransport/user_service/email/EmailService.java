package com.urbantransport.user_service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

  private final JavaMailSender javaMailSender;

  public void sendEmail(String to, String subject, String htmlContent)
    throws MessagingException, UnsupportedEncodingException {
    MimeMessage message = javaMailSender.createMimeMessage();

    message.setFrom(
      new InternetAddress("noreply@ims.ensias.com", "eTawsil Corporation")
    );
    message.setRecipients(
      MimeMessage.RecipientType.TO,
      InternetAddress.parse(to)
    );
    message.setSubject(subject);
    message.setContent(htmlContent, "text/html");
    javaMailSender.send(message);
  }
}
