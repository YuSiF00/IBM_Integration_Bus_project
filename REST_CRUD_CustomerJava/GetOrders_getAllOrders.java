import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import other.MyLogger;
import ua.com.integrity.JavaComputeNode;
import ua.com.integrity.logging.ClassicLogger;
import Model.Order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.broker.plugin.MbElement;

import dao.OrderDaoImpl;

public class GetOrders_getAllOrders extends JavaComputeNode {

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

					OrderDaoImpl orderDaoImpl = new OrderDaoImpl(conn, logger,
							messageId);
					List<Order> orders = new ArrayList<Order>();

					MbElement myMbElement = localEnvironment().getMbElement()
							.getFirstElementByPath(
									"REST/Input/Parameters/customerName");

					if (myMbElement == null) {

						logger.info(messageId, "MbElement : " + myMbElement);
						orders = orderDaoImpl.getAllOrders();

					} else {
						String customerName = myMbElement.getValueAsString();
						logger.info(messageId, "customerName " + customerName);
						
						orders = orderDaoImpl
								.getOrdersByCustomerName(customerName);

					}

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
