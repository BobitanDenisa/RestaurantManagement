package business;

import java.util.ArrayList;
import java.util.Objects;

public class CompositeProduct extends MenuItem {

	private static final long serialVersionUID = -8875205074066089302L;
	private ArrayList<MenuItem> items;

	public CompositeProduct() {
		super();
	}

	public CompositeProduct(String name) {
		super(name);
	}

	public CompositeProduct(String name, ArrayList<MenuItem> list) {
		super(name);
		double price = 0;
		for (MenuItem item : list) {
			price += item.computePrice();
		}
		this.setPrice(price);
		this.items = list;
	}

	public ArrayList<MenuItem> getItems() {
		return this.items;
	}

	public void setItems(ArrayList<MenuItem> list) {
		this.items = list;
	}

	public boolean contains(MenuItem item) {
		for (MenuItem i : this.items) {
			if (i.equals(item)) {
				return true;
			}
		}
		return false;
	}

	public double computePrice() {
		double sum = 0;
		for (MenuItem item : this.items) {
			sum += item.computePrice();
		}
		return sum;
	}

}
