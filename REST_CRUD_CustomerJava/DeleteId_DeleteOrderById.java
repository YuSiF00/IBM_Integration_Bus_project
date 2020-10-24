import java.sql.Connection;

import other.MyLogger;
import ua.com.integrity.JavaComputeNode;
import ua.com.integrity.logging.ClassicLogger;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.OrderDaoImpl;

public class DeleteId_DeleteOrderById extends JavaComputeNode {

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

					int orderId = Integer.parseInt(localEnvironment()
							.getMbElement()
							.getFirstElementByPath("REST/Input/Parameters/id")
							.getValueAsString());

					logger.info(messageId, "orderId " + orderId);

					OrderDaoImpl orderDaoImpl = new OrderDaoImpl(conn, logger,
							messageId);

					setJsonOutput(mapper.writeValueAsString(orderDaoImpl
							.deleteOrderById(orderId)));

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
