package hu.webuni.webshop.order.reopository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.webshop.order.model.WsOrder;

public interface OrderRepository extends JpaRepository<WsOrder, Long>{

}
