package Model;

public class MyError {

	private String error_message;
	
	private int code;

	public MyError() {
		
	}

	public MyError(String error_message, int http_code) {
		this.error_message = error_message;
		this.code = http_code;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "MyError [error_message=" + error_message + ", code=" + code
				+ "]";
	}

	
	
	
	
	
	
	
	
	
}
