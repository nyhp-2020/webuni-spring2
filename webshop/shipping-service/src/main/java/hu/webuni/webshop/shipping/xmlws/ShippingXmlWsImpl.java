package hu.webuni.webshop.shipping.xmlws;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import hu.webuni.webshop.shipping.dto.ShipmentOrderDto;
import hu.webuni.webshop.shipping.dto.ShipmentOrderDto.ShipmentState;
import hu.webuni.webshop.shipping.dto.ShipmentOrderItemDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShippingXmlWsImpl implements ShippingXmlWs{
	
	private Random random = new Random();
	
//	@Async
	@Override
	public int sendOrder(ShipmentOrderDto orderDto) {
		System.out.println(orderDto.getId());
		System.out.println(orderDto.getUsername());
		ArrayList<ShipmentOrderItemDto> items = orderDto.getItems();
		items.forEach(i -> {
			System.out.println(i.getProductname());
		});
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		
		int rnd = random.nextInt(0, 99);
		ShipmentState state;
		if (rnd < 50) {
			state = ShipmentState.SHIPMENT_FAILED;
		}else {
			state = ShipmentState.DELIVERED;
		}
		
		//Üzenet sorba üzenet (state,id)
		
		
		return random.nextInt(0, 1000); //as shipmentId
		
	}

}
