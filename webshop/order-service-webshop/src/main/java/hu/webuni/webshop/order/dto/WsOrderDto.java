package hu.webuni.webshop.order.dto;

import java.util.ArrayList;

import hu.webuni.webshop.order.model.OrderItem;
import hu.webuni.webshop.order.model.WsOrder.OrderState;
import lombok.Data;

@Data
public class WsOrderDto {
	long id;
	String deliveryAddress;
	ArrayList<OrderItemDto> items;
//	WebshopUserDto webshopUser;
	String username;
	OrderState orderState;
}
