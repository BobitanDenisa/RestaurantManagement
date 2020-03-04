package presentation;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import business.MenuItem;
import business.Order;
import business.Restaurant;
import data.RestaurantSerializator;

public class WaiterGUI extends JFrame {

	private static final long serialVersionUID = -2672061874445476839L;
	private JLabel t;
	private JTextField table;
	private JLabel p;
	private JTable ptable;
	private JScrollPane sp1;
	private JLabel o;
	private JTable otable;
	private JScrollPane sp2;
	private JButton add;
	private JButton inc;
	private JButton dec;
	private JButton delete;
	private JButton order;
	private JButton computeBill;
	private JTextField quantity;
	private JLabel q;
	private JLabel total;
	private Restaurant restaurant;
	private double sum;
	private HashMap<MenuItem, Integer> items;

	static int orderID = 0;

	public WaiterGUI(Restaurant r) {

		this.restaurant = r;

		this.sum = 0;

		this.items = new HashMap<MenuItem, Integer>();

		TableGenerator<MenuItem> tg = new TableGenerator<MenuItem>();

		this.t = new JLabel("Table:");
		this.t.setBounds(40, 40, 50, 20);

		this.table = new JTextField();
		this.table.setBounds(100, 40, 50, 20);

		this.p = new JLabel("Products:");
		this.p.setBounds(40, 100, 100, 20);

		this.ptable = tg.createTable(new MenuItem());
		this.sp1 = new JScrollPane(this.ptable);
		this.sp1.setBounds(40, 150, 200, 210);

		this.q = new JLabel("Quantity:");
		this.q.setBounds(270, 150, 70, 20);

		this.quantity = new JTextField();
		this.quantity.setBounds(340, 150, 50, 20);

		this.add = new JButton("Add");
		this.add.setBounds(430, 150, 100, 30);

		this.inc = new JButton("+");
		this.inc.setBounds(430, 210, 50, 30);

		this.dec = new JButton("-");
		this.dec.setBounds(430, 270, 50, 30);

		this.delete = new JButton("Delete");
		this.delete.setBounds(430, 330, 100, 30);

		this.order = new JButton("Order");
		this.order.setBounds(40, 430, 100, 30);

		this.o = new JLabel("Order:");
		this.o.setBounds(560, 100, 100, 20);

		this.otable = tg.createTable(new MenuItem());
		DefaultTableModel model = (DefaultTableModel) otable.getModel();
		model.addColumn("quantity");
		this.sp2 = new JScrollPane(this.otable);
		this.sp2.setBounds(560, 150, 300, 210);

		this.total = new JLabel("TOTAL: " + sum + " LEI");
		this.total.setBounds(560, 380, 100, 20);

		this.computeBill = new JButton("Compute bill");
		this.computeBill.setBounds(40, 490, 250, 30);

		this.add(t);
		this.add(table);

		this.add(p);
		this.add(sp1);

		this.add(o);
		this.add(sp2);

		this.add(add);
		this.add(inc);
		this.add(dec);
		this.add(delete);

		this.add(order);
		this.add(computeBill);

		this.add(q);
		this.add(quantity);

		this.add(total);

		this.setTitle("Waiter");
		this.setLayout(null);
		this.setSize(new Dimension(920, 600));

		setProductsTableInfo();

	}

	public void addWAddButtonListener(ActionListener a) {
		add.addActionListener(a);
	}

	public void addWIncButtonListener(ActionListener a) {
		inc.addActionListener(a);
	}

	public void addWDecButtonListener(ActionListener a) {
		dec.addActionListener(a);
	}

	public void addWDeleteButtonListener(ActionListener a) {
		delete.addActionListener(a);
	}

	public void addWOrderButtonListener(ActionListener a) {
		order.addActionListener(a);
	}

	public void addWComputeBillButtonListener(ActionListener a) {
		computeBill.addActionListener(a);
	}

	public void setProductsTableInfo() {
		DefaultTableModel model = (DefaultTableModel) ptable.getModel();
		RestaurantSerializator ser = new RestaurantSerializator();
		HashSet<MenuItem> list = ser.deserialize();
		model.setRowCount(list.size());
		int i = -1;
		for (MenuItem item : list) {
			if (item != null) {
				i++;
				model.setValueAt(item.getName(), i, 0);
				model.setValueAt(item.computePrice(), i, 1);
			}
		}
	}

	public MenuItem getSelectedItem() { // get selected item from big table (with all the menu items)
		int row = ptable.getSelectedRow();
		if (row != -1) {
			return restaurant.getItem(ptable.getValueAt(row, 0).toString());
		}
		JOptionPane.showMessageDialog(null, "Please select an item from the products table!", "ERROR",
				JOptionPane.ERROR_MESSAGE);
		return null;
	}

	public MenuItem getSelectedItemFromOrder() {
		int row = otable.getSelectedRow();
		if (row != -1) {
			return restaurant.getItem(otable.getValueAt(row, 0).toString());
		}
		JOptionPane.showMessageDialog(null, "Please select a product from the order table!", "ERROR",
				JOptionPane.ERROR_MESSAGE);
		return null;
	}

	@SuppressWarnings("rawtypes")
	public int getQuantity(MenuItem item) {
		Iterator it = items.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			if (((MenuItem) pair.getKey()).getName().equals(item.getName())) {
				return (Integer) pair.getValue();
			}
		}
		return 0;
	}

	public void addItemToOrder(MenuItem item) {
		if (item != null) {
			int q = getQuantity(item);

			items.remove(item);
			if (!quantity.getText().isEmpty()) {
				q += Integer.valueOf(quantity.getText());
			} else {
				q += 1;
			}
			items.put(item, Integer.valueOf(q));
			setOrderTableInfo();
		}
	}

	public void incItemFromOrder(MenuItem item) {
		if (item != null) {
			int q = getQuantity(item);
			items.remove(item);
			q += 1;
			items.put(item, Integer.valueOf(q));
			setOrderTableInfo();
		}
	}

	public void deleteItemFromOrder(MenuItem item) {
		if (item != null) {
			if (items.remove(item) != null) {
				setOrderTableInfo();
			} else {
				JOptionPane.showMessageDialog(null, "Cannot delete item that does not exist!", "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void decItemFromOrder(MenuItem item) {
		if (item != null) {
			int q;
			if ((q = getQuantity(item)) != 0) {
				items.remove(item);
			}
			q -= 1;
			if (q > 0) {
				items.put(item, Integer.valueOf(q));
			} else if (q == 0) {
				items.remove(item);
			}
			setOrderTableInfo();
		}
	}

	@SuppressWarnings("rawtypes")
	public void setOrderTableInfo() {
		DefaultTableModel model = (DefaultTableModel) otable.getModel();
		model.setRowCount(items.size());
		sum = 0;
		Iterator it = items.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			String name = ((MenuItem) pair.getKey()).getName();
			double price = ((MenuItem) pair.getKey()).computePrice();
			int qnt = ((Integer) pair.getValue());
			model.setValueAt(name, i, 0);
			model.setValueAt(price, i, 1);
			model.setValueAt(qnt, i, 2);
			sum += price * qnt;
			i++;
		}

		// update total sum in the label
		this.total.setText("TOTAL: " + sum + " LEI");
	}

	public void submitOrder() { // save the order into the Restaurant class
		int t = 0;
		if (!this.table.getText().isEmpty()) {
			t = Integer.valueOf(this.table.getText());
			Order ord = null;
			if (t > 0) {
				orderID++;
				ord = new Order(orderID, t, sum);
			} else {
				JOptionPane.showMessageDialog(null, "Invalid table number!", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			restaurant.createNewOrder(ord, items);
		} else {
			JOptionPane.showMessageDialog(null, "Please select a table!", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void computeBill() {
		Order ord = new Order(orderID, Integer.valueOf(this.table.getText()), sum);
		restaurant.generateBill(ord);
	}

	public void cleatWaiterData() {
		DefaultTableModel model = (DefaultTableModel) otable.getModel();
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
		sum = 0;
		this.total.setText("TOTAL: " + sum + " LEI");
		this.items.clear();
	}

}
