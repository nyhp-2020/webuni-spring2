package hu.webuni.webshop.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.webshop.order.ShipmentOrderDto;
import hu.webuni.webshop.order.ShipmentOrderItemDto;
import hu.webuni.webshop.order.dto.OrderItemDto;
import hu.webuni.webshop.order.dto.WsOrderDto;
import hu.webuni.webshop.order.model.OrderItem;
import hu.webuni.webshop.order.model.WsOrder;

@Mapper(componentModel = "spring")
public interface ShippingMapper {
	
	@Mapping(target = "wsorder",ignore = true)
	ShipmentOrderItemDto OrderItemToDto(OrderItem orderItem);
	
	@Mapping(target = "orderState",ignore = true)
	ShipmentOrderDto WsOrderToShipmentOrderDto(WsOrder order);

}
