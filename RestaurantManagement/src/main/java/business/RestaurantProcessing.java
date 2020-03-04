package business;

import java.util.HashMap;

public interface RestaurantProcessing {

	/**
	 * @param item - the item to be added to the menu items list
	 * @pre item!=null && !menuItems.contains(item)
	 * @post pre@ menuItems.size()==menuItems.size()+1
	 * @inv isWellFormed()
	 */
	public void createNewMenuItem(MenuItem item);

	/**
	 * @param item - the item to be deleted from the menu items list if it exists
	 * @pre item!=null && menuItems.size()>0 && menuItems.contains(item)
	 * @post !menuItems.contains(item) && @pre menuItems.size()==menuItems.size()-1
	 * @inv isWellFormed()
	 */
	public void deleteMenuItem(MenuItem item);

	/**
	 * @param oldItem - the item to be replaced
	 * @param newItem - the item to replace with
	 * @pre oldItem!=null && newItem!=null && menuItems.contains(oldItem)
	 * @post !menuItems.contains(oldItem) && @pre menuItems.size()==menuItems.size()
	 * @inv isWellFormed()
	 */
	public void editMenuItem(MenuItem oldItem, MenuItem newItem);

	/**
	 * @param order - the order to be entered into the hash map
	 * @param list  - the list of products corresponding to the order
	 * @pre order!=null && list!=null && !orderComponents.containsKey(order)
	 * @post @pre orderComponents.size()==orderComponents.size()+1;
	 * @inv isWellFormed()
	 */
	public void createNewOrder(Order order, HashMap<MenuItem, Integer> list);

	/**
	 * @param order - the order for which the total price is computed
	 * @return double - the value of the computed price
	 * @pre order!=null
	 * @post @nochange
	 * @inv isWellFormed()
	 */
	public double computePrice(Order order);

	/**
	 * @param order - the order for which a bill is generated
	 * @pre order!=null
	 * @post @nochange
	 * @inv isWellFormed()
	 */
	public void generateBill(Order order);

}
