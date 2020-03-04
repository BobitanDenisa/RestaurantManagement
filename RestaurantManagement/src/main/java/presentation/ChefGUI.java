package presentation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import business.MenuItem;

public class ChefGUI extends JFrame implements Observer {

	private static final long serialVersionUID = -7851090745774812941L;
	private JTable table;
	private JScrollPane sp;

	public ChefGUI() {

		String[] columnNames = new String[2];
		columnNames[0] = "Name";
		columnNames[1] = "Quantity";
		int rowCount = 0;
		DefaultTableModel model = new DefaultTableModel(columnNames, rowCount);
		this.table = new JTable(model);
		this.sp = new JScrollPane(this.table);
		this.sp.setBounds(10, 10, 480, 460);

		this.setTitle("Chef");
		this.setLayout(null);

		this.add(sp);

		this.setSize(520, 520);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void update(Observable arg0, Object arg1) {
		HashMap<MenuItem, Integer> list = (HashMap<MenuItem, Integer>) arg1;
		Iterator it = list.entrySet().iterator();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(list.size());
		int i = 0;
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			model.setValueAt(((MenuItem) pair.getKey()).getName(), i, 0);
			model.setValueAt((Integer) pair.getValue(), i, 1);
			i++;
		}
	}

}
