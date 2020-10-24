package model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "listOfCustomer")
public class ListOfCustomer {

	private List<Customer> customers;

	public ListOfCustomer() {

	}

	public ListOfCustomer(List<Customer> customers) {
		this.customers = customers;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

}
