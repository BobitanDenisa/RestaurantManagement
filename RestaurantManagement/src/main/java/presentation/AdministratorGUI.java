package presentation;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import business.MenuItem;
import business.Restaurant;
import data.RestaurantSerializator;

public class AdministratorGUI extends JFrame {

	private static final long serialVersionUID = 3472454384581453967L;
	private JLabel nameLabel;
	private JLabel priceLabel;
	private JTextField name;
	private JTextField price;
	private JTable table;
	private JScrollPane sp;
	private ButtonGroup selection;
	private JRadioButton base;
	private JRadioButton composite;
	private JTable compositeProd;
	private JScrollPane sp1;
	private JTable updateComposite;
	private JScrollPane sp2;
	private JButton add1;
	private JButton del1;
	private JButton up;
	private JButton add;
	private JButton del;
	private JButton insert;
	private JButton delete;
	private JButton update;
	private Restaurant restaurant;

	public AdministratorGUI(Restaurant r) {

		this.restaurant = r;

		this.nameLabel = new JLabel("Name");
		this.nameLabel.setBounds(40, 40, 50, 20);
		this.name = new JTextField();
		this.name.setBounds(110, 40, 150, 20);

		this.priceLabel = new JLabel("Price");
		this.priceLabel.setBounds(40, 90, 50, 20);
		this.price = new JTextField();
		this.price.setBounds(110, 90, 150, 20);

		SelectItemListener sel = new SelectItemListener();

		this.base = new JRadioButton("Base Product");
		this.base.setActionCommand("Base Product");
		this.base.setBounds(40, 140, 200, 20);
		this.base.setSelected(true);
		this.base.addActionListener(sel);

		this.composite = new JRadioButton("Composite Product");
		this.composite.setActionCommand("Composite Product");
		this.composite.setBounds(40, 180, 200, 20);
		this.composite.addActionListener(sel);

		this.selection = new ButtonGroup();
		this.selection.add(base);
		this.selection.add(composite);

		TableGenerator<MenuItem> tg = new TableGenerator<MenuItem>();
		this.compositeProd = tg.createTable(new MenuItem());
		this.sp1 = new JScrollPane(this.compositeProd);
		this.sp1.setBounds(40, 230, 220, 220);
		this.sp1.setVisible(false);

		this.add = new JButton("Add");
		this.add.setBounds(40, 460, 90, 30);
		this.add.setVisible(false);

		this.del = new JButton("Del");
		this.del.setBounds(170, 460, 90, 30);
		this.del.setVisible(false);

		this.insert = new JButton("Insert");
		this.insert.setBounds(40, 230, 220, 30);

		this.delete = new JButton("Delete");
		this.delete.setBounds(40, 290, 220, 30);

		this.updateComposite = tg.createTable(new MenuItem());
		this.sp2 = new JScrollPane(this.updateComposite);
		this.sp2.setBounds(40, 350, 220, 220);
		this.sp2.setVisible(false);

		this.add1 = new JButton("Add");
		this.add1.setBounds(40, 580, 60, 30);
		this.add1.setVisible(false);

		this.del1 = new JButton("Del");
		this.del1.setBounds(120, 580, 60, 30);
		this.del1.setVisible(false);

		this.up = new JButton("Up");
		this.up.setBounds(200, 580, 60, 30);
		this.up.setVisible(false);

		this.update = new JButton("Update");
		this.update.setBounds(40, 350, 220, 30);

		this.table = tg.createTable(new MenuItem());
		this.sp = new JScrollPane(this.table);
		this.sp.setBounds(300, 40, 600, 650);

		this.setTitle("Admin");
		this.setLayout(null);

		this.add(nameLabel);
		this.add(name);
		this.add(priceLabel);
		this.add(price);

		this.add(sp);

		this.add(insert);

		this.add(base);
		this.add(composite);
		this.add(delete);
		this.add(update);

		this.add(add);
		this.add(del);
		this.add(sp1);

		this.add(add1);
		this.add(del1);
		this.add(up);
		this.add(sp2);

		this.setSize(new Dimension(960, 770));

		setTableInfo();

	}

	public String getName() {
		if (this.name.getText().isEmpty())
			return null;
		return this.name.getText();
	}

	public double getPrice() {
		if (this.price.getText().isEmpty())
			return -1;
		return Double.valueOf(this.price.getText());
	}

	public ArrayList<MenuItem> getItems() { // from the composite product table, when inserting
		ArrayList<MenuItem> list = new ArrayList<MenuItem>();
		int n = this.compositeProd.getRowCount();
		for (int i = 0; i < n; i++) {
			list.add(new MenuItem(compositeProd.getValueAt(i, 0).toString(),
					Double.valueOf(compositeProd.getValueAt(i, 1).toString())));
		}
		return list;
	}

	public boolean emptyName() { // returns true if name field is empty
		if (this.name.getText().isEmpty())
			return true;
		return false;
	}

	public boolean emptyPrice() { // returns true if price field is empty
		if (this.price.getText().isEmpty())
			return true;
		return false;
	}

	public void addInsertButtonListener(ActionListener a) {
		insert.addActionListener(a);
	}

	public void addUpdateButtonListener(ActionListener a) {
		update.addActionListener(a);
	}

	public void addDeleteButtonListener(ActionListener a) {
		delete.addActionListener(a);
	}

	public void addAddButtonListener(ActionListener a) {
		add.addActionListener(a);
	}

	public void addDelButtonListener(ActionListener a) {
		del.addActionListener(a);
	}

	public void addAddCompButtonListener(ActionListener a) {
		add1.addActionListener(a);
	}

	public void addDelCompButtonListener(ActionListener a) {
		del1.addActionListener(a);
	}

	public void addUpCompButtonListener(ActionListener a) {
		up.addActionListener(a);
	}

	public MenuItem getSelectedItem() { // get selected item from big table (with all the menu items)
		int row = table.getSelectedRow();
		if (row != -1) {
			return this.restaurant.getItem(table.getValueAt(row, 0).toString());
		}
		return null;
	}

	public String getItemSelection() { // get the string corresponding to each of the 2 product types: base or
										// composite
		if (base.isSelected())
			return "base";
		if (composite.isSelected())
			return "composite";
		return null;
	}

	public void setCompositeProductTableInfo(MenuItem item) { // fill composite product table when inserting
		DefaultTableModel model = (DefaultTableModel) compositeProd.getModel();
		model.setRowCount(model.getRowCount() + 1);
		model.setValueAt(item.getName(), model.getRowCount() - 1, 0);
		model.setValueAt(item.computePrice(), model.getRowCount() - 1, 1);

		// update price in the text field
		if (this.price.getText().isEmpty()) {
			this.price.setText(Double.valueOf(item.computePrice()).toString());
		} else {
			Double res = Double.valueOf(this.price.getText()) + Double.valueOf(item.computePrice());
			this.price.setText(res.toString());
		}
	}

	public void deleteComponentFromTable() { // delete from composite product table
		DefaultTableModel model = (DefaultTableModel) compositeProd.getModel();
		Double price = Double.valueOf(compositeProd.getValueAt(compositeProd.getSelectedRow(), 1).toString());
		model.removeRow(compositeProd.getSelectedRow());

		// update price in the text field
		Double res = Double.valueOf(this.price.getText()) - Double.valueOf(price);
		this.price.setText(res.toString());
	}

	public void resetRadioButtons() { // select base radio button
		this.base.setSelected(true);
	}

	public void setTableInfo() { // fill big table with info in the .ser file
		DefaultTableModel model = (DefaultTableModel) table.getModel();
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

	public void showInsert() { // extend area for insertion of composite products
		sp1.setVisible(true);
		add.setVisible(true);
		del.setVisible(true);
		insert.setLocation(40, 540);
		delete.setLocation(40, 600);
		update.setLocation(40, 660);
		disablePrice();
	}

	public void hideInsert() { // make area extended area not visible
		sp1.setVisible(false);
		add.setVisible(false);
		del.setVisible(false);
		insert.setLocation(40, 230);
		delete.setLocation(40, 290);
		update.setLocation(40, 350);
		enablePrice();
	}

	private void disablePrice() { // disable writing in the price field - only for the composite products
		this.price.setText("");
		this.price.setEnabled(false);
	}

	private void enablePrice() { // enable writing in the price field
		this.price.setText("");
		this.price.setEnabled(true);
	}

	public void resetFields() {
		this.price.setText("");
		this.name.setText("");
	}

	public void resetTable() { // remove data from composite product table
		DefaultTableModel model = (DefaultTableModel) this.compositeProd.getModel();
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}

	public void showUpdate() {
		sp2.setVisible(true);
		add1.setVisible(true);
		del1.setVisible(true);
		up.setVisible(true);
		update.setVisible(false);
		disablePrice();
	}

	public void hideUpdate() {
		sp2.setVisible(false);
		add1.setVisible(false);
		del1.setVisible(false);
		up.setVisible(false);
		update.setVisible(true);
		enablePrice();
	}

	public void fillUpdateTable(ArrayList<MenuItem> list) {
		DefaultTableModel model = (DefaultTableModel) updateComposite.getModel();
		model.setRowCount(list.size());
		int i = 0;
		double res = 0;
		for (MenuItem item : list) {
			model.setValueAt(item.getName(), i, 0);
			model.setValueAt(item.computePrice(), i, 1);
			res += item.computePrice();
			i++;
		}

		// set price field with the total price of the product
		this.price.setText(Double.valueOf(res).toString());
	}

	public void setUpdateCompositeTableInfo(MenuItem item) {
		DefaultTableModel model = (DefaultTableModel) updateComposite.getModel();
		model.setRowCount(model.getRowCount() + 1);
		model.setValueAt(item.getName(), model.getRowCount() - 1, 0);
		model.setValueAt(item.computePrice(), model.getRowCount() - 1, 1);

		// update price in the text field
		if (this.price.getText().isEmpty()) {
			this.price.setText(Double.valueOf(item.computePrice()).toString());
		} else {
			Double res = Double.valueOf(this.price.getText()) + Double.valueOf(item.computePrice());
			this.price.setText(res.toString());
		}
	}

	public void deleteUpdateCompFromTable() {
		DefaultTableModel model = (DefaultTableModel) updateComposite.getModel();
		Double price = Double.valueOf(updateComposite.getValueAt(updateComposite.getSelectedRow(), 1).toString());
		model.removeRow(updateComposite.getSelectedRow());

		// update price in the text field
		Double res = Double.valueOf(this.price.getText()) - Double.valueOf(price);
		this.price.setText(res.toString());
	}

	public ArrayList<MenuItem> getUpdateItems() {
		ArrayList<MenuItem> list = new ArrayList<MenuItem>();
		int n = this.updateComposite.getRowCount();
		for (int i = 0; i < n; i++) {
			list.add(new MenuItem(updateComposite.getValueAt(i, 0).toString(),
					Double.valueOf(updateComposite.getValueAt(i, 1).toString())));
		}
		return list;
	}

	class SelectItemListener implements ActionListener { // listener for the radio buttons' selection
		public void actionPerformed(ActionEvent e) {
			if (selection.getSelection().getActionCommand().equals("Composite Product")) {
				showInsert();
			} else if (selection.getSelection().getActionCommand().equals("Base Product")) {
				hideInsert();
			}
		}
	}

}
