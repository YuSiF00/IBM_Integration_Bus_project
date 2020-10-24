import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import model.Customer;
import model.ListOfCustomer;

import org.w3c.dom.Document;

import other.ExceptionLogger;
import ua.com.integrity.JavaComputeNode;
import ua.com.integrity.logging.ClassicLogger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbXMLNS;

public class GetCustomers_GetAllCustomersByXML extends JavaComputeNode {

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
					MbElement bodyElement = getInputRootElement()
							.getFirstElementByPath("JSON/Data");
					String body = new String(bodyElement.toBitstream("", "",
							"", 1208, 1208, 0));
					logger.trace(getMessageId(), body);
					ObjectMapper mapper = new ObjectMapper();

					ListOfCustomer listOfCustomer = new ListOfCustomer();

					listOfCustomer.setCustomers(mapper.readValue(body,
							new TypeReference<List<Customer>>() {
							}));

					JAXBContext contextObj = JAXBContext
							.newInstance(ListOfCustomer.class);

					Marshaller marshallerObj = contextObj.createMarshaller();
					marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
							true);

					Document doc = getOutMessage().createDOMDocument(
							MbXMLNS.PARSER_NAME);

					marshallerObj.marshal(listOfCustomer, doc);

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
