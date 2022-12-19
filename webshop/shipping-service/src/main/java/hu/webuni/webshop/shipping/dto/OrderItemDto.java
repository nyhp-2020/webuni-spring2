package hu.webuni.webshop.shipping.dto;

import lombok.Data;

@Data
public class OrderItemDto {
	long id;
	long quantity;
	double orderPrice;
	WsOrderDto wsorder;
//	ProductDto product;
	
	String productname;
	long productId;
}
