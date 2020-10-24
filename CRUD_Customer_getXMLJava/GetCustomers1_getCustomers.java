import java.util.ArrayList;
import java.util.List;

import model.Customer;
import other.ExceptionLogger;
import ua.com.integrity.JavaComputeNode;
import ua.com.integrity.logging.ClassicLogger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.broker.plugin.MbElement;

public class GetCustomers1_getCustomers extends JavaComputeNode {

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
					MbElement bodyElement = getInputRootElement()
							.getFirstElementByPath("JSON/Data");
					String body = new String(bodyElement.toBitstream("", "",
							"", 1208, 1208, 0));
					logger.trace(getMessageId(), body);
					ObjectMapper mapper = new ObjectMapper();

					@SuppressWarnings("unused")
					List<Customer> customers = new ArrayList<Customer>();

					customers = mapper.readValue(body,
							new TypeReference<List<Customer>>() {
							});
					globalEnvironment().set("customers", body);

				} catch (Exception e) {
					ExceptionLogger exLogger = new ExceptionLogger(logger, e,
							messageId);
					exLogger.getLogger();
				}

				propagate();
				return false;
			}
		};
	}

}
