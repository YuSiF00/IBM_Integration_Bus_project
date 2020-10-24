import java.sql.Connection;

import other.MyLogger;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.com.integrity.JavaComputeNode;
import ua.com.integrity.logging.ClassicLogger;
import dao.CustomerDaoImpl;

public class DeleteCustomerById_DeleteCustomerById extends JavaComputeNode {

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

					CustomerDaoImpl customerDaoImpl = new CustomerDaoImpl(conn,
							logger, messageId);

					setJsonOutput(mapper.writeValueAsString(customerDaoImpl
							.deleteCustomerById(customerId)));

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