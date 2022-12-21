package hu.webuni.webshop.order.jms;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import hu.webuni.webshop.order.service.OrderService;
import hu.webuni.webshop.shipping.ws.ShippingStatusMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ShippingStatusMessageConsumer {
	
	private final OrderService orderService;
	
	@JmsListener(destination = "shippingstatus")
	public void onStatusMessage(ShippingStatusMessage statusMessage) {
		System.out.println(statusMessage);
		long id = statusMessage.getId();
		boolean delivered = statusMessage.isDelivered();
		if(delivered)
			orderService.setOrderDelivered(id);
		else
			orderService.setOrderShipmentFailed(id);
	}

}
