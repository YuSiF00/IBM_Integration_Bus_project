package model;

//@XmlRootElement(name = "order")
//@XmlAccessorType(XmlAccessType.FIELD)
public class Order {

	private int order_id;
	private String description;

	public Order() {

	}

	public Order(int order_id, String description) {
		this.order_id = order_id;
		this.description = description;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
