package business;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Order {

	private final int id;
	private final Date date;
	private final int table;
	private final double total;

	public Order(int id, int table, double t) {
		this.id = id;
		this.table = table;
		this.date = new Date();
		this.total = t;
	}

	public int getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public int getTable() {
		return table;
	}

	public double getTotal() {
		return total;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Order other = (Order) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return "OrderID: " + this.id + ", " + df.format(this.date) + ", tebleID: " + this.table;
	}

}
