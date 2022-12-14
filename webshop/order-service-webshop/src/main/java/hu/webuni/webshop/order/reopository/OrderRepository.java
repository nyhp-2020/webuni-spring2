package hu.webuni.webshop.order.reopository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.webshop.order.model.WsOrder;

public interface OrderRepository extends JpaRepository<WsOrder, Long>{
	
	@EntityGraph(attributePaths = {"items"})
	@Query("SELECT o FROM WsOrder o WHERE o.id = :id")
	WsOrder findByIdWithItems(long id);
	
	@EntityGraph(attributePaths = {"items"})
//	@Query("SELECT o FROM WsOrder o WHERE o.username LIKE :username")
	List<WsOrder> findByUsername(String username);
}
