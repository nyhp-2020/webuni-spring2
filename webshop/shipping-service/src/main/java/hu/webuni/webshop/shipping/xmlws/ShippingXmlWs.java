package hu.webuni.webshop.shipping.xmlws;

import javax.jws.WebService;
import javax.xml.ws.ResponseWrapper;

import hu.webuni.webshop.shipping.dto.ShipmentOrderDto;

@WebService
public interface ShippingXmlWs {
	
	@ResponseWrapper(localName = "sendOrderResponse",className = "java.lang.Integer")
	public int sendOrder(ShipmentOrderDto wsOrderDto);

}
