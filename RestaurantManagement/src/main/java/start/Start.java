package start;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import business.BaseProduct;
import business.CompositeProduct;
import business.MenuItem;
import business.Restaurant;
import presentation.AdministratorGUI;
import presentation.ChefGUI;
import presentation.View;
import presentation.WaiterGUI;

public class Start {

	private View view;
	private AdministratorGUI admin;
	private WaiterGUI waiter;
	public static ChefGUI chef;

	private Restaurant restaurant;

	public Start() {

		chef = new ChefGUI();

		this.restaurant = new Restaurant();

		this.view = new View();
		this.admin = new AdministratorGUI(restaurant);
		this.waiter = new WaiterGUI(restaurant);

		this.view.setVisible(true);
		this.view.addAdminButtonListener(new AdminButtonListener());
		this.view.addWaiterButtonListener(new WaiterButtonListener());
		this.view.addChefButtonListener(new ChefButtonListener());
	}

	/*
	 * main view listeners
	 */

	class AdminButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			admin.addInsertButtonListener(new InsertItemButtonListener());
			admin.addAddButtonListener(new AddItemButtonListener());
			admin.addDelButtonListener(new DelItemButtonListener());
			admin.addAddCompButtonListener(new AddCompButtonListener());
			admin.addDelCompButtonListener(new DelCompButtonListener());
			admin.addUpCompButtonListener(new UpCompButtonListener());
			admin.addUpdateButtonListener(new UpdateItemButtonListener());
			admin.addDeleteButtonListener(new DeleteItemButtonListener());
			admin.setVisible(true);
		}
	}

	class WaiterButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			waiter.addWAddButtonListener(new WAddButtonListener());
			waiter.addWIncButtonListener(new WIncButtonListener());
			waiter.addWDecButtonListener(new WDecButtonListener());
			waiter.addWDeleteButtonListener(new WDeleteButtonListener());
			waiter.addWOrderButtonListener(new WOrderButtonListener());
			waiter.addWComputeBillButtonListener(new WComputeBillButtonListener());
			waiter.setVisible(true);
		}
	}

	class ChefButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			chef.setVisible(true);
		}
	}

	/*
	 * administrator view listeners
	 */

	class InsertItemButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!admin.emptyName() && !admin.emptyPrice()) {
				String s = admin.getItemSelection();
				if (s.equals("base")) {
					BaseProduct bp = new BaseProduct(admin.getName(), admin.getPrice());
					restaurant.createNewMenuItem(bp);
				}
				if (s.equals("composite")) {
					CompositeProduct cp = new CompositeProduct(admin.getName(), admin.getItems());
					restaurant.createNewMenuItem(cp);
				}
				admin.setTableInfo();
				waiter.setProductsTableInfo();
				admin.hideInsert();
				admin.resetFields();
				admin.resetTable();
				admin.resetRadioButtons();
			} else {
				JOptionPane.showMessageDialog(null, "Please fill the name and price fields", "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class AddItemButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MenuItem item = admin.getSelectedItem();
			admin.setCompositeProductTableInfo(item);
		}
	}

	class DelItemButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			admin.deleteComponentFromTable();
		}
	}

	class UpdateItemButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MenuItem item = restaurant.getItem(admin.getSelectedItem().getName());
			String name = admin.getName();
			if (item instanceof CompositeProduct) {
				admin.showUpdate();
				admin.fillUpdateTable(((CompositeProduct) item).getItems());
			} else {
				double price = admin.getPrice();
				MenuItem newItem = new MenuItem();
				if (name != null) {
					newItem.setName(name);
				} else {
					newItem.setName(item.getName());
				}
				if (price != -1) {
					newItem.setPrice(price);
				} else {
					newItem.setPrice(item.computePrice());
				}
				restaurant.editMenuItem(item, newItem);
				admin.setTableInfo();
				waiter.setProductsTableInfo();
				admin.resetFields();
			}
		}
	}

	class DeleteItemButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MenuItem item = admin.getSelectedItem();
			restaurant.deleteMenuItem(item);
			admin.setTableInfo();
			waiter.setProductsTableInfo();
		}
	}

	class AddCompButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MenuItem item = admin.getSelectedItem();
			admin.setUpdateCompositeTableInfo(item);
		}
	}

	class DelCompButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			admin.deleteUpdateCompFromTable();
		}
	}

	class UpCompButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			CompositeProduct item = (CompositeProduct) restaurant.getItem(admin.getSelectedItem().getName());
			String name = admin.getName();
			CompositeProduct newItem = new CompositeProduct();
			if (name != null) {
				newItem.setName(name);
			} else {
				newItem.setName(item.getName());
			}
			newItem.setPrice(admin.getPrice());
			newItem.setItems(admin.getUpdateItems());
			restaurant.editMenuItem(item, newItem);
			admin.setTableInfo();
			waiter.setProductsTableInfo();
			admin.resetFields();
			admin.hideUpdate();
		}
	}

	/*
	 * waiter view listeners
	 */

	class WAddButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MenuItem item = waiter.getSelectedItem();
			waiter.addItemToOrder(item);
		}
	}

	class WIncButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MenuItem item = waiter.getSelectedItemFromOrder();
			waiter.incItemFromOrder(item);
		}
	}

	class WDecButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MenuItem item = waiter.getSelectedItemFromOrder();
			waiter.decItemFromOrder(item);
		}
	}

	class WDeleteButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MenuItem item = waiter.getSelectedItemFromOrder();
			waiter.deleteItemFromOrder(item);
		}
	}

	class WOrderButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			waiter.submitOrder();
		}
	}

	class WComputeBillButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			waiter.computeBill();
			waiter.cleatWaiterData();
		}
	}

	public static void main(String[] args) {
		Start start = new Start();
	}

}
