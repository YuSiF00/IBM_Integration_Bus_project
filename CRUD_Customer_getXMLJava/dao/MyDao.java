package dao;

import java.util.List;

import model.Account;
import model.Customer;

public interface MyDao {
	
	public List<Customer> getCustomersWithAccounts(List<Customer> customers, List<Account> accounts ) throws Exception;


}
