package hu.webuni.webshop.shipping.dto;

import java.util.ArrayList;

import lombok.Data;

@Data
public class ShipmentOrderDto {
	public enum ShipmentState {
		SHIPMENT_FAILED,
		DELIVERED
		;
	}
	
	long id;
	String deliveryAddress;
	ArrayList<ShipmentOrderItemDto> items;

	String username;
	ShipmentState orderState;
	
	String admissionAddress;
}
