package com.SpringNotificationHub.NotificationServ;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import com.SpringNotificationHub.NotificationServ.model.BroadcastChannel;
import com.SpringNotificationHub.NotificationServ.model.NotificationEntity;
import com.SpringNotificationHub.NotificationServ.model.ChannelType;
import com.SpringNotificationHub.NotificationServ.service.EmailService;
import com.SpringNotificationHub.NotificationServ.service.GeneratorNotification;
import com.SpringNotificationHub.NotificationServ.service.SmsService;

@Import(TestcontainersConfiguration.class)
class NotificationServApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test 
	public void testarGeradorNotificacao(){
		List<BroadcastChannel> canal = new ArrayList<>();
		
		NotificationEntity emailNotification = new NotificationEntity();
		NotificationEntity smsNotificacao = new NotificationEntity();

		// EmailService email = new EmailService();
		SmsService sms = new SmsService();

		// GeneratorNotification gerenciador =  new GeneratorNotification(canal);
		
		// emailNotification.setType(ChannelType.EMAIL);
		emailNotification.setMessage("essa mensagem eh a notificacao do email");

		

		// smsNotificacao.setType(ChannelType.SMS);
		// smsNotificacao.setMessage("mensagem do sms");
		
		// sms.setNumero("719865925006");
		// sms.type();

		// email.setEnderecoEmail("kauvergasta@gmail.com");
		// email.type();


		// canal.add(email);
		canal.add(sms);

		
		// gerenciador.generatorMsg(emailNotification);

	}

}
