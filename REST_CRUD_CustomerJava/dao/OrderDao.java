package dao;

import java.util.List;

import Model.Order;

public interface OrderDao {
	
	
	public List<Order> getAllOrders() throws Exception;
	
	public List<Order> getOrdersByCustomer(Integer customerId) throws Exception;
	
	public List<Order> getOrdersByCustomerName(String customerName) throws Exception;
	
	public Order deleteOrderById(int theId) throws Exception;
	
	public Order addOrder(Order order, Integer customerId) throws Exception;
	
	public Order updateOrder(Order order, Integer customerId) throws Exception;

}
