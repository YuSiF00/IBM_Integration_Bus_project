package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.internal.OracleTypes;
import ua.com.integrity.logging.ClassicLogger;
import Model.Account;
import Model.Customer;
import Model.Order;

public class CustomerDaoImpl implements CustomerDao {

	private Connection conn;
	@SuppressWarnings("unused")
	private ClassicLogger classicLogger;

	public CustomerDaoImpl(Connection conn, ClassicLogger classicLogger,
			String messageId) {
		this.conn = conn;
		this.classicLogger = classicLogger;
	}

	@Override
	public List<Customer> getAllCustomers() throws Exception {
		List<Customer> customers = new ArrayList<>();
		ResultSet rs = null;

		try (CallableStatement stmn = conn
				.prepareCall("{call dwh.CRUD_Customer.getCustomers(?)}")) {
			stmn.registerOutParameter("p_out_cursor", OracleTypes.CURSOR);
			stmn.execute();
			rs = (ResultSet) stmn.getObject("p_out_cursor");

			while (rs.next()) {
				Customer customer = new Customer(rs.getInt("id"),
						rs.getString("name"), rs.getString("surname"),
						rs.getString("email"), rs.getString("address"));

				customer.setOrders(getOrdersByCustomer2(customer.getId()));
				customers.add(customer);
			}

		}

		return customers;
	}

	@Override
	public Customer getCustomerById(int theId) throws Exception {

		Customer customer = null;
		ResultSet rs = null;

		try (CallableStatement stmn2 = conn
				.prepareCall("{call dwh.CRUD_Customer.getCustomerById(?,?)}")) {
			stmn2.setInt("p_id", theId);
			stmn2.registerOutParameter("p_out_cursor", OracleTypes.CURSOR);
			stmn2.execute();
			rs = (ResultSet) stmn2.getObject("p_out_cursor");

			while (rs.next()) {
				customer = new Customer(rs.getInt("id"), rs.getString("name"),
						rs.getString("surname"), rs.getString("email"),
						rs.getString("address"));

				customer.setOrders(getOrdersByCustomer2(customer.getId()));
			}

		}
		
		

		return customer;

	}

	@Override
	public Customer save(Customer customer) throws Exception {

		try (CallableStatement stmn = conn
				.prepareCall("{call dwh.CRUD_Customer.createCustomer(?,?,?,?,?)}")) {
			stmn.setInt(1, customer.getId());
			stmn.setString(2, customer.getName());
			stmn.setString(3, customer.getSurname());
			stmn.setString(4, customer.getEmail());
			stmn.setString(5, customer.getAddress());

			stmn.execute();

		}

		return customer;
	}

	@Override
	public Customer deleteCustomerById(int theId) throws Exception {

		Customer customer = new Customer();
		ResultSet rs = null;

		try (CallableStatement stmn = conn
				.prepareCall("{call dwh.CRUD_Customer.deleteCustomerById(?,?)}")) {
			stmn.setInt("p_id", theId);
			stmn.registerOutParameter("p_out_cursor", OracleTypes.CURSOR);
			stmn.execute();
			rs = (ResultSet) stmn.getObject("p_out_cursor");
			while (rs.next()) {
				customer = new Customer(rs.getInt("id"), rs.getString("name"),
						rs.getString("surname"), rs.getString("email"),
						rs.getString("address"));
			}

		}

		return customer;

	}

	@Override
	public Customer updateCustomer(Customer customer) throws Exception {

		try (CallableStatement stmn = conn
				.prepareCall("{call dwh.CRUD_Customer.updateCustomerById(?,?,?,?,?)}")) {
			stmn.setInt(1, customer.getId());
			stmn.setString(2, customer.getName());
			stmn.setString(3, customer.getSurname());
			stmn.setString(4, customer.getEmail());
			stmn.setString(5, customer.getAddress());

			stmn.execute();

		}
		return customer;
	}

	public List<Order> getOrdersByCustomer2(Integer customerId)
			throws Exception {

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

	public List<Account> getAccountsByCustomer2(Integer customerId)
			throws Exception {

		List<Account> accounts = new ArrayList<>();

		ResultSet rs = null;

		try (CallableStatement stmn = conn
				.prepareCall("{call dwh.CRUD_Customer.getAccountByCustomer(?,?)}")) {
			stmn.setInt("p_id", customerId);
			stmn.registerOutParameter("p_out_cursor", OracleTypes.CURSOR);
			stmn.execute();
			rs = (ResultSet) stmn.getObject("p_out_cursor");
			while (rs.next()) {
				Account account = new Account(rs.getInt("ACCOUNT_ID"),
						rs.getString("NAME"), rs.getString("CODE"),
						rs.getInt("BALANCE"));
				accounts.add(account);
			}

		}

		return accounts;
	}

}
