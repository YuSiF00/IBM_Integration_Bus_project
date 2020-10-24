import java.sql.Connection;
import java.util.List;

import other.MyLogger;
import ua.com.integrity.JavaComputeNode;
import ua.com.integrity.logging.ClassicLogger;
import Model.Order;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.OrderDaoImpl;

public class GetOrder_getOrderByCustomerId extends JavaComputeNode {

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

					OrderDaoImpl orderDaoImpl = new OrderDaoImpl(conn, logger,
							messageId);

					List<Order> orders = orderDaoImpl
							.getOrdersByCustomer(customerId);

					setJsonOutput(mapper.writeValueAsString(orders));

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
