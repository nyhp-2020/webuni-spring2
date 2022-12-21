package hu.webuni.webshop.shipping.service;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import hu.webuni.webshop.shipping.dto.ShipmentOrderDto.ShipmentState;
import hu.webuni.webshop.shipping.ws.ShippingStatusMessage;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class JmsService {
	
	private final JmsTemplate jmsTemplate;
	
	public void sendJmsMessage(long id, ShipmentState state) {
		ShippingStatusMessage message = new ShippingStatusMessage();
		message.setId(id);
		if(state == ShipmentState.DELIVERED )
			message.setDelivered(true);
		else
			message.setDelivered(false);
		
		jmsTemplate.convertAndSend("shippingstatus",message);
	}

}
