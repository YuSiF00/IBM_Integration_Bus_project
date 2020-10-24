import java.sql.Connection;

import other.MyLogger;
import ua.com.integrity.JavaComputeNode;
import ua.com.integrity.logging.ClassicLogger;
import Model.Order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.broker.plugin.MbElement;

import dao.OrderDaoImpl;

public class PutOrder_updateOrder extends JavaComputeNode {

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

					MbElement bodyElement = getInputRootElement()
							.getFirstElementByPath("JSON/Data");
					String body = new String(bodyElement.toBitstream("", "",
							"", 1208, 1208, 0));

					int customerId = Integer.parseInt(localEnvironment()
							.getMbElement()
							.getFirstElementByPath("REST/Input/Parameters/id")
							.getValueAsString());

					logger.info(messageId, "customerid " + customerId);

					Order order = mapper.readValue(body, Order.class);

					OrderDaoImpl orderDaoImpl = new OrderDaoImpl(conn, logger,
							messageId);

					setJsonOutput(mapper.writeValueAsString(orderDaoImpl
							.updateOrder(order, customerId)));

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
