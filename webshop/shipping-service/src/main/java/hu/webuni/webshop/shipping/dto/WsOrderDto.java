package hu.webuni.webshop.shipping.dto;

import java.util.ArrayList;

import lombok.Data;

@Data
public class WsOrderDto {
	public enum OrderState {
		PENDING, 
		CONFIRMED,
		DECLINED,
		SHIPMENT_FAILED,
		DELIVERED
		;
	}
	
	long id;
	String deliveryAddress;
	ArrayList<OrderItemDto> items;
//	WebshopUserDto webshopUser;
	String username;
	OrderState orderState;
}
