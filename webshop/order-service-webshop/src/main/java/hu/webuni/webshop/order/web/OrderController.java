package hu.webuni.webshop.order.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.webshop.order.dto.WsOrderDto;
import hu.webuni.webshop.order.mapper.OrderMapper;
import hu.webuni.webshop.order.model.WsOrder;
import hu.webuni.webshop.order.service.OrderService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	private final OrderMapper orderMapper;
//	private final ItemMapper itemMapper;
	private final OrderService orderService;
//	private final ItemService itemService;
//	private final OrderRepository orderRepository;
//	private final ItemRepository itemRepository;
	
	@PostMapping
	public WsOrderDto createOrder(@RequestBody WsOrderDto wsOrderDto) {
		
//		wsOrderDto.getItems().forEach(i -> {
//			i.setId(0);});
		WsOrder wsorder = orderMapper.dtoToWsOrder(wsOrderDto);
		wsorder = orderService.save(wsorder);
		return orderMapper.WsOrderToDto(wsorder);

	}
	
	
	@PutMapping("/confirm/{id}")
	public void confirmOrder(@PathVariable("id") long id, @RequestParam boolean confirmed) {
		if(confirmed) {
			orderService.setOrderConfirmed(id);
			//XML
		}
		else
			orderService.setOrderDeclined(id);
	}
	
	@GetMapping("/username")
	public List<WsOrderDto> findByUsername(@RequestParam String username){
		List<WsOrder> orders = orderService.findByUsernameIgnoreCase(username);
		return orderMapper.ordersToDtos(orders);
	}

}
