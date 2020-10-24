import java.sql.Connection;
import java.util.List;

import other.MyLogger;
import ua.com.integrity.JavaComputeNode;
import ua.com.integrity.logging.ClassicLogger;
import Model.Account;

import com.fasterxml.jackson.databind.ObjectMapper;


import dao.AccountDaoImpl;


public class GetAccount_getAccountforCustomer extends JavaComputeNode {


	@Override
	protected JavaComputeEvaluator getEvaluator() {
		// TODO Auto-generated method stub
		return new JavaComputeEvaluator() {

			@Override
			public boolean evaluate() throws Exception {

				ClassicLogger logger = new ClassicLogger(
						"CRUD_Customer",
						"C:\\IBM\\workspaces\\CRUD_Customer\\CRUD_Customer.log",
						"TRACE");

				try {
					assignMessageId();
					Connection conn = getJDBCType4Connection("Oracle");

					ObjectMapper mapper = new ObjectMapper();

					int customerId = Integer.parseInt(localEnvironment()
							.getMbElement()
							.getFirstElementByPath("REST/Input/Parameters/id")
							.getValueAsString());

					logger.info(messageId, "customerid " + customerId);

					AccountDaoImpl accountDaoImpl = new AccountDaoImpl(conn,
							logger, messageId);
					
					List<Account> accounts = accountDaoImpl.getAccountsByCustomer(customerId);


					setJsonOutput(mapper.writeValueAsString(accounts));

				} catch (Exception e) {
					MyLogger myLog = new MyLogger(logger, e, messageId);
					myLog.getLogger();
				}
				propagate();
				return false;
			}
		};
	}

}
