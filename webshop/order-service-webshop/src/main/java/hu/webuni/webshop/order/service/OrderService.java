package hu.webuni.webshop.order.service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.webshop.order.ShipmentOrderDto;
import hu.webuni.webshop.order.ShippingXmlWs;
import hu.webuni.webshop.order.ShippingXmlWsImplService;
import hu.webuni.webshop.order.mapper.ShippingMapper;
import hu.webuni.webshop.order.model.OrderItem;
import hu.webuni.webshop.order.model.WsOrder;
import hu.webuni.webshop.order.model.WsOrder.OrderState;
import hu.webuni.webshop.order.reopository.ItemRepository;
import hu.webuni.webshop.order.reopository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final OrderRepository orderRepository;
	
	private final ItemRepository itemRepository;
	
	private final ShippingMapper shippingMapper;

	@Transactional
	public WsOrder save(WsOrder wsorder) {
		Set<OrderItem> savedItems = new HashSet<>();
		
		wsorder.setOrderState(OrderState.PENDING);
		
		wsorder.getItems().forEach(orderItem -> {
			savedItems.add(itemRepository.save(orderItem));
		});
		
//		wsorder.setItems(savedItems);
//		WsOrder savedOrder = orderRepository.save(wsorder);
//		savedOrder.getItems().forEach(savedItem -> {
//			savedItem.setWsorder(savedOrder);
//		});
		
		wsorder.setItems(null);
		WsOrder savedOrder = orderRepository.save(wsorder);
		savedItems.forEach(savedItem -> {
			savedOrder.addOrderItem(savedItem);
		});
		return savedOrder;
	}
	
	@Transactional
	public WsOrder update(WsOrder wsorder) {
		if(orderRepository.existsById(wsorder.getId()))
				return orderRepository.save(wsorder);
		else
			throw new NoSuchElementException();
	}
	
	@Transactional
	public void delete(long id) {
		orderRepository.deleteById(id);
	}
	
	@Transactional
	public List<WsOrder> findByUsernameIgnoreCase(String username){
		return orderRepository.findAll();
	}
	
	@Transactional
	public void setOrderConfirmed(long id) {
		orderRepository.findById(id)
		.ifPresent(o -> o.setOrderState(OrderState.CONFIRMED));
	}
	
	@Transactional
	public void setOrderDeclined(long id) {
		orderRepository.findById(id)
		.ifPresent(o -> o.setOrderState(OrderState.DECLINED));
	}
	
	@Transactional
	public void setOrderShipmentFailed(long id) {
		orderRepository.findById(id)
		.ifPresent(o -> o.setOrderState(OrderState.SHIPMENT_FAILED));
	}
	
	@Transactional
	public void setOrderDelivered(long id) {
		orderRepository.findById(id)
		.ifPresent(o -> o.setOrderState(OrderState.DELIVERED));
	}
	

	public WsOrder getOrderByIdWithItems(long id) {
		return orderRepository.findByIdWithItems(id);	
	}
	
	public void callXmlService(long id) {
		
		ShippingXmlWs shippingXmlWsImplPort = new ShippingXmlWsImplService().getShippingXmlWsImplPort();
		
		WsOrder order = getOrderByIdWithItems(id);
		
		ShipmentOrderDto shipmentOrderDto = shippingMapper.WsOrderToShipmentOrderDto(order);
		
		shipmentOrderDto.setAdmissionAddress("Bp, Lehel piac");
		
		shippingXmlWsImplPort.sendOrder(shipmentOrderDto);
		
	}
}
