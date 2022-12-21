package hu.webuni.webshop.shipping.ws;

import lombok.Data;

@Data
public class ShippingStatusMessage {
	private long id;
	private boolean delivered;
}
