package hu.webuni.webshop.shipping.dto;

import lombok.Data;

@Data
public class ShipmentOrderItemDto {
	long id;
	long quantity;
	double orderPrice;
	ShipmentOrderDto wsorder;
	
	String productname;
	long productId;
}
