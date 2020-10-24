package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.internal.OracleTypes;
import ua.com.integrity.logging.ClassicLogger;
import Model.Order;

public class OrderDaoImpl implements OrderDao {

	private Connection conn;

	@SuppressWarnings("unused")
	private ClassicLogger logger;

	public OrderDaoImpl(Connection conn, ClassicLogger logger, String messageId) {
		this.conn = conn;
		this.logger = logger;
	}

	@Override
	public List<Order> getAllOrders() throws Exception {

		List<Order> orders = new ArrayList<>();

		ResultSet rs = null;

		try (CallableStatement stmn = conn
				.prepareCall("{call dwh.CRUD_Customer.getOrders(?)}")) {
			stmn.registerOutParameter("p_out_cursor", OracleTypes.CURSOR);
			stmn.execute();
			rs = (ResultSet) stmn.getObject("p_out_cursor");
			while (rs.next()) {
				Order order = new Order(rs.getInt("ORDER_ID"),
						rs.getString("DESCRIPTION"));
				orders.add(order);
			}

		}

		return orders;
	}

	@Override
	public List<Order> getOrdersByCustomer(Integer customerId) throws Exception {

		List<Order> orders = new ArrayList<>();

		ResultSet rs = null;

		try (CallableStatement stmn = conn
				.prepareCall("{call dwh.CRUD_Customer.getOrderByCustomer(?,?)}")) {
			stmn.setInt("p_id", customerId);
			stmn.registerOutParameter("p_out_cursor", OracleTypes.CURSOR);
			stmn.execute();
			rs = (ResultSet) stmn.getObject("p_out_cursor");
			while (rs.next()) {
				Order order = new Order(rs.getInt("ORDER_ID"),
						rs.getString("DESCRIPTION"));
				orders.add(order);
			}

		}

		return orders;
	}

	@Override
	public Order deleteOrderById(int theId) throws Exception {

		Order order = new Order();
		ResultSet rs = null;

		try (CallableStatement stmn = conn
				.prepareCall("{call dwh.CRUD_Customer.deleteOrdersById(?,?)}")) {
			stmn.setInt("p_id", theId);
			stmn.registerOutParameter("p_out_cursor", OracleTypes.CURSOR);
			stmn.execute();
			rs = (ResultSet) stmn.getObject("p_out_cursor");
			while (rs.next()) {
				order = new Order(rs.getInt("ORDER_ID"),
						rs.getString("DESCRIPTION"));
			}

		}

		return order;

	}

	@Override
	public Order addOrder(Order order, Integer customerId) throws Exception {

		try (CallableStatement stmn = conn
				.prepareCall("{call dwh.CRUD_Customer.addOrder(?,?,?)}")) {

			stmn.setInt(1, order.getOrder_id());
			stmn.setString(2, order.getDescription());
			stmn.setInt(3, customerId);

			stmn.execute();

		}

		return order;
	}

	@Override
	public Order updateOrder(Order order, Integer customerId) throws Exception {
		try (CallableStatement stmn = conn
				.prepareCall("{call dwh.CRUD_Customer.updateOrder(?,?,?)}")) {

			stmn.setInt(1, order.getOrder_id());
			stmn.setString(2, order.getDescription());
			stmn.setInt(3, customerId);

			stmn.execute();

		}

		return order;
	}

	@Override
	public List<Order> getOrdersByCustomerName(String customerName)
			throws Exception {

		List<Order> orders = new ArrayList<>();

		ResultSet rs = null;
		try (CallableStatement stmn = conn
				.prepareCall("{call dwh.CRUD_Customer.getOrderByCustomerName(?,?)}")) {
			stmn.setString("p_customerName", customerName);
			stmn.registerOutParameter("p_out_cursor", OracleTypes.CURSOR);
			stmn.execute();
			rs = (ResultSet) stmn.getObject("p_out_cursor");
			while (rs.next()) {
				Order order = new Order(rs.getInt("ORDER_ID"),
						rs.getString("DESCRIPTION"));
				orders.add(order);
			}

		}

		return orders;
	}

}
