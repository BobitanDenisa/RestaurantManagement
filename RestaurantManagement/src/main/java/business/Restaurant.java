package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

import javax.swing.JOptionPane;

import data.FileWriter;
import data.RestaurantSerializator;
import presentation.ChefGUI;
import start.Start;

public class Restaurant extends Observable implements RestaurantProcessing {

	private HashSet<MenuItem> menuItems;
	private HashMap<Order, HashMap<MenuItem, Integer>> orderComponents;
	private RestaurantSerializator serializator;
	private FileWriter fileWriter;

	private ChefGUI chef = Start.chef;

	public Restaurant() {

		RestaurantSerializator ser = new RestaurantSerializator();
		this.menuItems = ser.deserialize();
		this.orderComponents = new HashMap<Order, HashMap<MenuItem, Integer>>();
		this.serializator = new RestaurantSerializator();
		this.fileWriter = new FileWriter();

		this.addObserver(chef);

	}

	public HashSet<MenuItem> getMenuItems() {
		return this.menuItems;
	}

	public MenuItem containsItem(MenuItem item) {
		for (MenuItem i : menuItems) {
			if (i instanceof CompositeProduct) {
				if (((CompositeProduct) i).contains(item)) {
					CompositeProduct res = (CompositeProduct) i;
					return res;
				}
			}
		}
		return null;
	}

	public MenuItem getItem(String name) {
		for (MenuItem i : menuItems) {
			if (name.equals(i.getName())) {
				return i;
			}
		}
		return null;
	}

	public HashMap<MenuItem, Integer> getListForOrder(Order ord) {
		return orderComponents.get(ord);
	}

	public void createNewMenuItem(MenuItem item) {
		assert item != null;
		assert !menuItems.contains(item);
		int size = menuItems.size() - 1;
		this.menuItems.add(item);
		assert size == menuItems.size();
		this.serializator.serialize(this.menuItems);
		assert isWellFormed();
	}

	public void deleteMenuItem(MenuItem item) {
		assert item != null;
		assert menuItems.contains(item);
		int size = menuItems.size();
		if (this.menuItems.remove(item)) {
			MenuItem aux;
			while ((aux = containsItem(item)) != null && menuItems.size() > 0) {
				this.menuItems.remove(aux);

				assert size == 1 + menuItems.size();

			}
			this.serializator.serialize(this.menuItems);
		} else {
			JOptionPane.showMessageDialog(null, "Cannot delete menu item that does not exist!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		assert isWellFormed();
	}

	public void editMenuItem(MenuItem oldItem, MenuItem newItem) {
		assert oldItem != null;
		assert newItem != null;
		assert menuItems.contains(oldItem);
		int size = menuItems.size();
		if (this.menuItems.remove(oldItem)) {
			this.menuItems.add(newItem);
			assert !menuItems.contains(oldItem);
			assert size == menuItems.size();
			this.serializator.serialize(this.menuItems);
		} else {
			JOptionPane.showMessageDialog(null, "Cannot edit menu item that does not exist!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		assert isWellFormed();
	}

	public void createNewOrder(Order order, HashMap<MenuItem, Integer> list) { // send info to the chef
		assert order != null && list != null;
		assert !orderComponents.containsKey(order);
		int size = orderComponents.size();
		this.orderComponents.put(order, list);
		assert size == orderComponents.size() - 1;

		// notify the chef
		setChanged();
		notifyObservers(orderComponents.get(order));
		assert isWellFormed();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public double computePrice(Order order) {
		assert order != null;
		double sum = 0;
		Iterator it = this.orderComponents.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			ArrayList<MenuItem> list = (ArrayList<MenuItem>) pair.getValue();
			for (MenuItem item : list) {
				sum += item.computePrice();
			}
		}
		assert isWellFormed();
		return sum;
	}

	public void generateBill(Order order) {
		assert order != null;
		HashMap<MenuItem, Integer> list = (HashMap<MenuItem, Integer>) orderComponents.get(order);
		this.fileWriter.generateBill(order, list);
		assert isWellFormed();
	}

	/**
	 * 
	 * @return false - if the orderComponents contains any order with no products
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean isWellFormed() {
		Iterator it = orderComponents.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			if (((HashSet<MenuItem>) pair.getValue()).size() <= 0) {
				return false;
			}
		}
		return true;
	}

}
