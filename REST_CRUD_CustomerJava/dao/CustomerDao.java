package dao;

import java.util.List;

import Model.Customer;

public interface CustomerDao {

	public List<Customer> getAllCustomers() throws Exception;

	public Customer getCustomerById(int theId) throws Exception;

	public Customer save(Customer customer) throws Exception;

	public Customer deleteCustomerById(int theId) throws Exception;

	public Customer updateCustomer(Customer customer) throws Exception;

}
