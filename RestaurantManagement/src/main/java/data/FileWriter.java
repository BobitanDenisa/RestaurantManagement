package data;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import business.MenuItem;
import business.Order;

public class FileWriter {

	private final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

	@SuppressWarnings("rawtypes")
	public void generateBill(Order order, HashMap<MenuItem, Integer> items) {
		try {
			PrintWriter writer = new PrintWriter("order" + order.getId() + ".txt");
			writer.println(DATE_FORMAT.format(order.getDate()));
			writer.println("Order #" + order.getId() + " Table #" + order.getTable());
			writer.println();
			writer.println("Menu Items:");
			writer.println();
			double total = 0;
			Iterator it = items.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				MenuItem item = (MenuItem) pair.getKey();
				Integer quantity = (Integer) pair.getValue();
				writer.println(item.getName() + " - " + quantity + " x " + DECIMAL_FORMAT.format(item.computePrice())
						+ " LEI");
				total += item.computePrice() * quantity;
			}
			writer.println();
			writer.println("TOTAL: " + DECIMAL_FORMAT.format(total) + " LEI");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
