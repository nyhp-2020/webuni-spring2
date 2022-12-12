package hu.webuni.webshop.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.webshop.order.dto.OrderItemDto;
import hu.webuni.webshop.order.model.OrderItem;

@Mapper(componentModel = "spring")
public interface ItemMapper {
	@Mapping(target = "wsorder",ignore = true)
	@Mapping(target = "product",ignore = true)
	OrderItem DtoToOrderItem(OrderItemDto itemDto);
	@Mapping(target = "wsorder",ignore = true)
	@Mapping(target = "product",ignore = true)
	OrderItemDto OrderItemToDto(OrderItem orderItem);
}
