import java.sql.Connection;

import other.MyLogger;
import Model.Customer;
import Model.MyError;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.com.integrity.JavaComputeNode;
import ua.com.integrity.logging.ClassicLogger;
import dao.CustomerDaoImpl;

public class GetCustomerById_getCustomerByID extends JavaComputeNode {

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

					CustomerDaoImpl customerDaoImpl = new CustomerDaoImpl(conn,
							logger, messageId);
					int customerId = Integer.parseInt(localEnvironment()
							.getMbElement()
							.getFirstElementByPath("REST/Input/Parameters/id")
							.getValueAsString());

					logger.info(messageId, "customerid " + customerId);

					Customer customer = customerDaoImpl
							.getCustomerById(customerId);

					if (customer != null) {
						setJsonOutput(mapper.writeValueAsString(customer));
						propagate();
					} else {
						MyError myError = new MyError("Customer not found", 400);
						setJsonOutput(mapper.writeValueAsString(myError));
						propagateAlt();
					}

				} catch (Exception e) {
					MyLogger myLog = new MyLogger(logger, e, messageId);
					myLog.getLogger();
				}
				
				return false;
			}
		};
	}

}
