package presentation;

import java.lang.reflect.Field;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TableGenerator<T> {

	public JTable createTable(T t) {
		JTable table = new JTable();
		Field[] fields = t.getClass().getDeclaredFields();
		String[] s = new String[fields.length - 1];
		int i = -1;
		for (Field f : fields) {
			if (!f.getName().equals("serialVersionUID")) {
				i++;
				s[i] = f.getName();
			}
		}
		DefaultTableModel model = new DefaultTableModel(s, 0);
		table.setModel(model);
		return table;
	}

}
