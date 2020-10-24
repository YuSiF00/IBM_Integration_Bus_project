package model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "customer")
@XmlType(propOrder = { "id", "name", "surname", "email", "address", "orders",
		"accounts" })
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {

	@XmlElement(name = "customer_id")
	private int id;

	private String name;
	private String surname;
	private String email;
	private String address;

	@XmlElementWrapper(name = "orders")
	@XmlElement(name = "order")
	private List<Order> orders;

	@XmlElement(name = "accounts")
	private List<Account> accounts;

	public Customer() {
	}

	public Customer(int id, String name, String surname, String email,
			String address) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.address = address;
	}

	public Customer(int id, String name, String surname, String email,
			String address, List<Order> orders) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.address = address;
		this.orders = orders;
	}

	public Customer(int id, String name, String surname, String email,
			String address, List<Order> orders, List<Account> accounts) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.address = address;
		this.orders = orders;
		this.accounts = accounts;
	}

	// @XmlAttribute
	// @XmlElement(name="customer_id")
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	// @XmlElementWrapper(name = "orders")
	// @XmlElement(name = "order")
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	// @XmlElementWrapper(name = "accounts")
	// @XmlElement(name = "account")
	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", surname=" + surname
				+ ", email=" + email + ", address=" + address + ", orders="
				+ orders + ", accounts=" + accounts + "]";
	}

}
