package hu.webuni.webshop.order.reopository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.webshop.order.model.OrderItem;

public interface ItemRepository extends JpaRepository<OrderItem, Long>{

}
