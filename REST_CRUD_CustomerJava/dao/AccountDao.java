package dao;

import java.util.List;

import Model.Account;

public interface AccountDao {
	
	public List<Account> getAllAccounts() throws Exception;
	
	public List<Account> getAccountsByCustomer(Integer customerId) throws Exception;
	
	public List<Account> getAccountsByCustomerName(String customerName) throws Exception;
	
	public Account deleteAccountById(int theId) throws Exception;
	
	public Account addAccount(Account account, Integer customerId) throws Exception;
	
	public Account updateAccount(Account account, Integer customerId) throws Exception;
	
	public boolean makeTransaction(String customerName, String debitAccount, String creditAccount, int amount) throws Exception;
	
	public boolean makeTransaction2(String debitAccount, String creditAccount, int amount) throws Exception;
	
	public boolean makeTransaction3(String debitAccount, String creditAccount, int amount) throws Exception;

}
