package hu.webuni.webshop.order.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.webshop.order.model.OrderItem;
import hu.webuni.webshop.order.reopository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {
	
	private final ItemRepository itemRepository;
	
	@Transactional
	public OrderItem save(OrderItem orderItem) {
		return itemRepository.save(orderItem);
	}
	
	@Transactional
	public OrderItem update(OrderItem OrderItem) {
		if(itemRepository.existsById(OrderItem.getId()))
				return itemRepository.save(OrderItem);
		else
			throw new NoSuchElementException();
	}
	
	@Transactional
	public void delete(long id) {
		itemRepository.deleteById(id);
	}

}
