import java.sql.Connection;

import other.MyLogger;
import ua.com.integrity.JavaComputeNode;
import ua.com.integrity.logging.ClassicLogger;
import Model.Customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.broker.plugin.MbElement;

import dao.CustomerDaoImpl;

public class PostCustomers_postCustomers extends JavaComputeNode {

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

					MbElement bodyElement = getInputRootElement()
							.getFirstElementByPath("JSON/Data");
					String body = new String(bodyElement.toBitstream("", "",
							"", 1208, 1208, 0));
					ObjectMapper mapper = new ObjectMapper();
					Customer customer = mapper.readValue(body, Customer.class);

					CustomerDaoImpl customerDaoImpl = new CustomerDaoImpl(conn,
							logger, messageId);

					
					setJsonOutput(mapper.writeValueAsString(customerDaoImpl
							.save(customer)));

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