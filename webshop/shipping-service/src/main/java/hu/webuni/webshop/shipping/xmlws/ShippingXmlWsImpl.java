package hu.webuni.webshop.shipping.xmlws;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import javax.xml.ws.AsyncHandler;

import org.apache.cxf.annotations.UseAsyncMethod;
import org.apache.cxf.jaxws.ServerAsyncResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import hu.webuni.webshop.shipping.dto.ShipmentOrderDto;
import hu.webuni.webshop.shipping.dto.ShipmentOrderDto.ShipmentState;
import hu.webuni.webshop.shipping.dto.ShipmentOrderItemDto;
import hu.webuni.webshop.shipping.service.JmsService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShippingXmlWsImpl implements ShippingXmlWs{
	
	private final JmsService jmsService;
	
	private Random random = new Random();
	
	@Override
	@UseAsyncMethod
	public int sendOrder(ShipmentOrderDto orderDto) {
		long orderId = orderDto.getId();
		System.out.println(orderId);
		System.out.println(orderDto.getUsername());
		ArrayList<ShipmentOrderItemDto> items = orderDto.getItems();
		items.forEach(i -> {
			System.out.println(i.getProductname());
		});
		
				
		int rnd = random.nextInt(0, 99);
		ShipmentState state;
		if (rnd < 50) {
			state = ShipmentState.SHIPMENT_FAILED;
		}else {
			state = ShipmentState.DELIVERED;
		}
		
		//Üzenet sorba üzenet (state,id)
		jmsService.sendJmsMessage(orderId, state);
		
		
		return longresponse(); //as shipmentId
		
	}

	private int longresponse() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		return random.nextInt(0, 1000);
	}
	
	@Async
	public CompletableFuture<Integer> longresponseAsync() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		return CompletableFuture.completedFuture(random.nextInt(0, 1000));
	}
	
	public Future<Integer> sendOrderAsync(ShipmentOrderDto orderDto, AsyncHandler<Integer> asyncHandler) {
		ServerAsyncResponse<Integer> serverAsyncResponse = new ServerAsyncResponse<>();
		System.out.println(Thread.currentThread().getName());
		
		long ordeId = orderDto.getId();
		System.out.println(ordeId);
		System.out.println(orderDto.getUsername());
		ArrayList<ShipmentOrderItemDto> items = orderDto.getItems();
		items.forEach(i -> {
			System.out.println(i.getProductname());
		});
		
				
		int rnd = random.nextInt(0, 99);
		ShipmentState state;
		if (rnd < 50) {
			state = ShipmentState.SHIPMENT_FAILED;
		}else {
			state = ShipmentState.DELIVERED;
		}
		
		//üzenetküldés
		
		jmsService.sendJmsMessage(ordeId, state);
		
//		jmsTemplate.convertAndSend("shippingstatus");
		
		longresponseAsync().thenAccept(result -> {
			System.out.println(Thread.currentThread().getName());
			serverAsyncResponse.set(result);
			asyncHandler.handleResponse(serverAsyncResponse);
		});
		
		return  serverAsyncResponse;
	}

}
