package business;

import java.io.Serializable;
import java.text.DecimalFormat;

public class MenuItem implements Serializable {

	private static final long serialVersionUID = 939182104846371144L;
	private String name;
	private double price;

	public MenuItem() {

	}

	public MenuItem(String name) {
		this.name = name;
	}

	public MenuItem(String name, double price) {
		this.name = name;
		this.price = price;
	}

	public double computePrice() {
		return this.price;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}

//	@Override
//	public int hashCode() {
//		return Objects.hash(this.name);
//	}
//
//	@Override
//	public boolean equals(Object o) {
//		MenuItem item = (MenuItem) o;
//		return (item.name.equals(this.name));
//	}

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("#.##");
		return "Menu item: " + this.name + " - " + df.format(this.price) + " RON";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuItem other = (MenuItem) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
