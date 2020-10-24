import java.util.ArrayList;
import java.util.List;

import model.Account;
import model.Customer;
import other.ExceptionLogger;
import ua.com.integrity.JavaComputeNode;
import ua.com.integrity.logging.ClassicLogger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.broker.plugin.MbElement;

import dao.MyDaoImpl;

public class GetCustomers1_getCustomersWithAccounts extends JavaComputeNode {

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

					List<Account> accounts = new ArrayList<>();

					accounts = mapper.readValue(body,
							new TypeReference<List<Account>>() {
							});

					String customersString = globalEnvironment().getAsString(
							"customers");

					List<Customer> customers = null;

					customers = mapper.readValue(customersString,
							new TypeReference<List<Customer>>() {
							});

					MyDaoImpl myDaoImpl = new MyDaoImpl(logger, messageId);

					List<Customer> result = myDaoImpl.getCustomersWithAccounts(
							customers, accounts);

					logger.trace(messageId, "customers : " + customers);
					logger.trace(messageId, "accounts : " + accounts);

					logger.trace(messageId, "result : " + result);

					setJsonOutput(mapper.writeValueAsString(result));

					

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
