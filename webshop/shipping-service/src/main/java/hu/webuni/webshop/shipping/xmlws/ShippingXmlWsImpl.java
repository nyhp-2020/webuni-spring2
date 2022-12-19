package hu.webuni.webshop.shipping.xmlws;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import hu.webuni.webshop.shipping.dto.OrderItemDto;
import hu.webuni.webshop.shipping.dto.WsOrderDto;
import hu.webuni.webshop.shipping.dto.WsOrderDto.OrderState;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShippingXmlWsImpl implements ShippingXmlWs{
	
	private Random random = new Random();
	
	@Async
	@Override
	public int sendOrder(WsOrderDto wsOrderDto) {
		System.out.println(wsOrderDto.getUsername());
		ArrayList<OrderItemDto> items = wsOrderDto.getItems();
		System.out.println(items.get(0).getProductname());
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		
		int rnd = random.nextInt(0, 99);
		OrderState state;
		if (rnd < 50) {
			state = OrderState.SHIPMENT_FAILED;
		}else {
			state = OrderState.DELIVERED;
		}
		
		//Üzenet sorba üzenet (state)
		
		
		return rnd;
		
	}

}
