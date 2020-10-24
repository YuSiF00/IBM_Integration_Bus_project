
import java.sql.Connection;
import java.util.List;

import other.MyLogger;
import ua.com.integrity.JavaComputeNode;
import ua.com.integrity.logging.ClassicLogger;
import Model.Customer;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.CustomerDaoImpl;

public class GetAllCustomers_getAllCustomers extends JavaComputeNode {

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
					ObjectMapper mapper = new ObjectMapper();
					Connection conn = getJDBCType4Connection("Oracle");

					CustomerDaoImpl customerDaoImpl = new CustomerDaoImpl(conn,
							logger, messageId);
					List<Customer> customers = customerDaoImpl
							.getAllCustomers();
					
					

					String outJson = mapper.writeValueAsString(customers);
					setJsonOutput(outJson);

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
