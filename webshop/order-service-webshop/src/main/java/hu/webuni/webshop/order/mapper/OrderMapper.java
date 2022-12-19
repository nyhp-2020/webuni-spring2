package hu.webuni.webshop.order.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.webshop.order.dto.OrderItemDto;
import hu.webuni.webshop.order.dto.WsOrderDto;
import hu.webuni.webshop.order.model.OrderItem;
import hu.webuni.webshop.order.model.WsOrder;

@Mapper(componentModel = "spring")
public interface OrderMapper {

//	@Mapping(target = "wsorder",ignore = true)
////	@Mapping(target = "product",ignore = true)
//	OrderItem DtoToOrderItem(OrderItemDto itemDto);
//	
//	@Mapping(target = "wsorder",ignore = true)
////	@Mapping(target = "product",ignore = true)
//	OrderItemDto OrderItemToDto(OrderItem orderItem);
	
//	@Mapping(target = "wsorder",ignore = true)
	OrderItem DtoToOrderItem(OrderItemDto itemDto);
	@Mapping(target = "wsorder",ignore = true)
	OrderItemDto OrderItemToDto(OrderItem orderItem);
	
	Set<OrderItem> listToSet(ArrayList<OrderItemDto> items);
	ArrayList<OrderItemDto> setToList(Set<OrderItem> items);
	
	WsOrder dtoToWsOrder(WsOrderDto orderDto);

	WsOrderDto WsOrderToDto(WsOrder order);
	
	List<WsOrderDto> ordersToDtos(List<WsOrder> orders);

}
