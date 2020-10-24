import java.sql.Connection;

import other.MyLogger;
import ua.com.integrity.JavaComputeNode;
import ua.com.integrity.logging.ClassicLogger;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.AccountDaoImpl;


public class PostMakeTransaction_MakeTransaction extends JavaComputeNode {

	
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
					

					String debitAccount = getInputRootElement()
							.getFirstElementByPath("JSON/Data/debitAccount").getValueAsString();
					
					String creditAccount = getInputRootElement()
							.getFirstElementByPath("JSON/Data/creditAccount").getValueAsString();
					
					
					int amount = Integer.parseInt(getInputRootElement()
							.getFirstElementByPath("JSON/Data/amount").getValueAsString());
					
					AccountDaoImpl accountDaoImpl = new AccountDaoImpl(conn, logger, getMessageId());
					
					
					boolean resultTransaction =   accountDaoImpl.makeTransaction3(debitAccount, creditAccount, amount);
					
					
					
					if(resultTransaction){
						setJsonOutput(mapper.writeValueAsString("Transaction is successfully"));
					}else{
						setJsonOutput(mapper.writeValueAsString("Transaction is not successfully"));
					}
					
					

					logger.info(messageId, "\nDebit Account " + debitAccount +"\nCreditAccount "+ creditAccount+"\nAmount "+ amount);
					

					
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
