package hu.webuni.webshop.shipping.xmlws;

import javax.jws.WebService;

import hu.webuni.webshop.shipping.dto.WsOrderDto;

@WebService
public interface ShippingXmlWs {
	
	public int sendOrder(WsOrderDto wsOrderDto);

}
