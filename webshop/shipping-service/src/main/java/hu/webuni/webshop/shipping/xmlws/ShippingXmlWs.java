package hu.webuni.webshop.shipping.xmlws;

import javax.jws.WebService;

import hu.webuni.webshop.shipping.dto.ShipmentOrderDto;

@WebService
public interface ShippingXmlWs {
	
	public int sendOrder(ShipmentOrderDto wsOrderDto);

}
