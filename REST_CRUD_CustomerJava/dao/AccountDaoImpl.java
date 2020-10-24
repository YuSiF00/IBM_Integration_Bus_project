package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




import oracle.jdbc.internal.OracleTypes;
import ua.com.integrity.logging.ClassicLogger;
import Model.Account;
import Model.Customer;

public class AccountDaoImpl implements AccountDao {

	private Connection conn;

	@SuppressWarnings("unused")
	private ClassicLogger logger;

	public AccountDaoImpl(Connection conn, ClassicLogger logger,
			String messageId) {
		this.conn = conn;
		this.logger = logger;
	}

	@Override
	public List<Account> getAllAccounts() throws Exception {
		List<Account> accounts = new ArrayList<>();

		ResultSet rs = null;

		try (CallableStatement stmn = conn
				.prepareCall("{call dwh.CRUD_Customer.getAccounts(?)}")) {
			stmn.registerOutParameter("p_out_cursor", OracleTypes.CURSOR);
			stmn.execute();
			rs = (ResultSet) stmn.getObject("p_out_cursor");
			while (rs.next()) {
				Account account = new Account(rs.getInt("ACCOUNT_ID"),
						rs.getString("NAME"), rs.getString("CODE"),
						rs.getInt("BALANCE"), rs.getInt("CUSTOMER_ID"));
				accounts.add(account);
			}

		}

		return accounts;
	}

	@Override
	public List<Account> getAccountsByCustomer(Integer customerId)
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

	@Override
	public List<Account> getAccountsByCustomerName(String customerName)
			throws Exception {
		List<Account> accounts = new ArrayList<>();
		ResultSet rs = null;

		try (CallableStatement stmn = conn
				.prepareCall("{call dwh.CRUD_Customer.getAccountByCustomerName(?,?)}")) {
			stmn.setString("p_customerName", customerName);
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

	@Override
	public Account deleteAccountById(int theId) throws Exception {
		Account account = new Account();
		ResultSet rs = null;

		try (CallableStatement stmn = conn
				.prepareCall("{call dwh.CRUD_Customer.deleteAccountsById(?,?)}")) {
			stmn.setInt("p_id", theId);
			stmn.registerOutParameter("p_out_cursor", OracleTypes.CURSOR);
			stmn.execute();
			rs = (ResultSet) stmn.getObject("p_out_cursor");
			while (rs.next()) {
				account = new Account(rs.getInt("ACCOUNT_ID"),
						rs.getString("NAME"), rs.getString("CODE"),
						rs.getInt("BALANCE"));
			}

		}

		return account;

	}

	@Override
	public Account addAccount(Account account, Integer customerId)
			throws Exception {
		try (CallableStatement stmn = conn
				.prepareCall("{call dwh.CRUD_Customer.addAccount(?,?,?,?,?)}")) {

			stmn.setInt(1, account.getId());
			stmn.setString(2, account.getName());
			stmn.setString(3, account.generateRandomCode());
			stmn.setInt(4, account.getBalance());
			stmn.setInt(5, customerId);

			stmn.execute();

		}

		return account;
	}

	@Override
	public Account updateAccount(Account account, Integer customerId)
			throws Exception {
		try (CallableStatement stmn = conn
				.prepareCall("{call dwh.CRUD_Customer.updateAccount(?,?,?,?,?)}")) {

			stmn.setInt(1, account.getId());
			stmn.setString(2, account.getName());
			stmn.setString(3, account.getCode());
			stmn.setInt(4, account.getBalance());
			stmn.setInt(5, customerId);

			stmn.execute();

		}

		return account;
	}

	@Override
	public boolean makeTransaction(String customerName, String debitAccount,
			String creditAccount, int amount) throws Exception {

		List<Account> accounts = getAccountsByCustomerName(customerName);

		Account account1 = new Account();
		Account account2 = new Account();

		boolean check1 = false;
		boolean check2 = false;

		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getName().equals(debitAccount)) {
				account1 = accounts.get(i);
				int balance1 = account1.getBalance();
				balance1 -= amount;
				account1.setBalance(balance1);
				updateAccountById(account1);
				check1 = true;

			}

			if (accounts.get(i).getName().equals(creditAccount)) {
				account2 = accounts.get(i);
				int balance2 = account2.getBalance();
				balance2 += amount;
				account2.setBalance(balance2);
				// customer = getCustomerByName(customerName);
				updateAccountById(account2);
				check2 = true;

			}
		}

		if (check1 && check2) {
			return true;
		} else {
			return false;
		}

	}

	public Customer getCustomerByName(String customerName) throws Exception {

		List<Customer> customers = new ArrayList<>();
		ResultSet rs = null;

		try (CallableStatement stmn = conn
				.prepareCall("{call dwh.CRUD_Customer.getCustomerByName(?,?)}")) {
			stmn.setString("p_name", customerName);
			stmn.registerOutParameter("p_out_cursor", OracleTypes.CURSOR);
			stmn.execute();
			rs = (ResultSet) stmn.getObject("p_out_cursor");

			while (rs.next()) {
				Customer customer = new Customer(rs.getInt("id"),
						rs.getString("name"), rs.getString("surname"),
						rs.getString("email"), rs.getString("address"));

				customers.add(customer);
			}

		}

		return customers.get(0);

	}

	public Account updateAccountById(Account account) throws Exception {

		try (CallableStatement stmn = conn
				.prepareCall("{call dwh.CRUD_Customer.updateAccountById(?,?,?,?)}")) {
			stmn.setInt(1, account.getId());
			stmn.setString(2, account.getName());
			stmn.setString(3, account.getCode());
			stmn.setInt(4, account.getBalance());

			stmn.execute();

		}
		return account;

	}

	@Override
	public boolean makeTransaction2(String debitAccount, String creditAccount,
			int amount) throws Exception {

		String check;

		try (CallableStatement stmn = conn
				.prepareCall("{call dwh.CRUD_Customer.makeTransaction(?,?,?,?)}")) {

			stmn.setString("p_account1Name", debitAccount);
			stmn.setString("p_account2Name", creditAccount);
			stmn.setInt("p_amount", amount);

			stmn.registerOutParameter("p_checker", OracleTypes.VARCHAR);
			stmn.execute();
			
			check = stmn.getString("p_checker");

			

		}

		if (check.equals("true")) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean makeTransaction3(String debitAccount, String creditAccount,
			int amount) throws Exception {
		
		try (CallableStatement stmn = conn
				.prepareCall("{call dwh.CRUD_Customer.makeTransaction2(?,?,?)}")) {

			stmn.setString("p_account1Name", debitAccount);
			stmn.setString("p_account2Name", creditAccount);
			stmn.setInt("p_amount", amount);

			stmn.execute();
			
		}catch (SQLException e) {
			if(e.getErrorCode() == 20777){
				return false;
			}
		}
		
		
		

		return true;
	}

}
