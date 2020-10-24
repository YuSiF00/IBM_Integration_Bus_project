package dao;

import java.util.ArrayList;
import java.util.List;

import ua.com.integrity.logging.ClassicLogger;
import model.Account;
import model.Customer;

public class MyDaoImpl implements MyDao {

	private ClassicLogger logger;
	private String messageId;

	public MyDaoImpl(ClassicLogger logger, String messageId) {
		this.logger = logger;
		this.messageId = messageId;
	}

	@Override
	public List<Customer> getCustomersWithAccounts(List<Customer> customers,
			List<Account> accounts) throws Exception {
		
		
		logger.trace(messageId, "Customers : " + customers);
		logger.trace(messageId, "accounts : " + accounts);



		try {

			for (int i = 0; i < customers.size(); i++) {

				List<Account> myAccounts = new ArrayList<Account>();

				for (int j = 0; j < accounts.size(); j++) {

					if (customers.get(i).getId() == accounts.get(j)
							.getCustomer_id()) {
						myAccounts.add(accounts.get(j));
					}

				}

				customers.get(i).setAccounts(myAccounts);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customers;
	}

}
