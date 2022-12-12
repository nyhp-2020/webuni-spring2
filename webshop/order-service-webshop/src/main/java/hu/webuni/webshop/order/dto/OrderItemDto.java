package hu.webuni.webshop.order.dto;

import lombok.Data;

@Data
public class OrderItemDto {
	long id;
	long quantity;
	double orderPrice;
	WsOrderDto wsorder;
	ProductDto product;
}
