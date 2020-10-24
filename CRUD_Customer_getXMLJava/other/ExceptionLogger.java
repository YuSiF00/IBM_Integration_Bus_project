package other;

import java.io.PrintWriter;
import java.io.StringWriter;

import ua.com.integrity.logging.ClassicLogger;

public class ExceptionLogger {
	

	private ClassicLogger logger;
	private Exception e;
	private String messageId;
	
	
	
	public ExceptionLogger(ClassicLogger logger, Exception e, String messageId) {
		this.logger = logger;
		this.e = e;
		this.messageId = messageId;
	}
	
	
	
	public void getLogger(){
		StringWriter sw = new StringWriter();
		PrintWriter printWriter = new PrintWriter(sw);
		e.printStackTrace(printWriter);
		logger.trace(messageId, sw.toString());
		}


}
