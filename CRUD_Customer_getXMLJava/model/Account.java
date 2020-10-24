package model;

import java.util.Random;

//@XmlRootElement(name = "account")
//@XmlAccessorType(XmlAccessType.FIELD)
public class Account {

	private int id;

	private String name;

	private String code;

	private int balance;
	
	private int customer_id;
	

	public Account() {

	}

	public Account(String name, String code, int balance) {
		this.name = name;
		this.code = code;
		this.balance = balance;
	}

	public Account(int id, String name, String code, int balance) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.balance = balance;
	}
	
	

	public Account(int id, String name, String code, int balance,
			int customer_id) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.balance = balance;
		this.customer_id = customer_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	

	
	

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", code=" + code
				+ ", balance=" + balance + ", customer_id=" + customer_id + "]";
	}

	public String generateRandomCode() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 20;
		Random random = new Random();

		String generatedString = random
				.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				.limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint,
						StringBuilder::append).toString();

		return generatedString;

	}

}
