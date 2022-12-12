package hu.webuni.webshop.order.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.webshop.order.model.WsOrder;
import hu.webuni.webshop.order.reopository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final OrderRepository orderRepository;

	@Transactional
	public WsOrder save(WsOrder wsorder) {
		return orderRepository.save(wsorder);
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
}
