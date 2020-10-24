import java.sql.Connection;

import other.MyLogger;
import ua.com.integrity.JavaComputeNode;
import ua.com.integrity.logging.ClassicLogger;

import com.fasterxml.jackson.databind.ObjectMapper;


import dao.AccountDaoImpl;


public class DeleteAccountById_DeleteAccountById extends JavaComputeNode {

	@Override
	protected JavaComputeEvaluator getEvaluator() {

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

					int account_id = Integer.parseInt(localEnvironment()
							.getMbElement()
							.getFirstElementByPath("REST/Input/Parameters/id")
							.getValueAsString());

					logger.info(messageId, "account " + account_id);
					
					AccountDaoImpl accountDaoImpl = new AccountDaoImpl(conn, logger, messageId);


					setJsonOutput(mapper.writeValueAsString(accountDaoImpl.deleteAccountById(account_id)));

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
