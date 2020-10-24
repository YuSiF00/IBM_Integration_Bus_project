import ua.com.integrity.JavaComputeNode;

import com.ibm.broker.plugin.MbElement;

public class GetCustomerById_setStatusCode extends JavaComputeNode {

	@Override
	protected JavaComputeEvaluator getEvaluator() {
		return new JavaComputeEvaluator() {

			@Override
			public boolean evaluate() throws Exception {

				outputRoot().set(inputRoot());

				MbElement headerElement = getOutputRootElement()
						.createElementAsFirstChild("HTTPReplyHeader");
				headerElement.createElementAsLastChild(
						MbElement.TYPE_NAME_VALUE,
						"X-Original-HTTP-Status-Code", 400);
				propagate();
				return false;
			}
		};
	}

}
