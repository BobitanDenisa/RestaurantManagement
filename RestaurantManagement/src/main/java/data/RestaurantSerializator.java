package data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import business.MenuItem;

public class RestaurantSerializator {

	private final String FILENAME = "menuitems.ser";

	public RestaurantSerializator() {

	}

	public void serialize(HashSet<MenuItem> items) {
		try {
			FileOutputStream file = new FileOutputStream(FILENAME);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(items);
			out.close();
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public HashSet<MenuItem> deserialize() {
		HashSet<MenuItem> list = new HashSet<MenuItem>();
		try {
			FileInputStream file = new FileInputStream(FILENAME);
			if (file.available() > 0) {
				ObjectInputStream in = new ObjectInputStream(file);
				list = (HashSet<MenuItem>) in.readObject();
				in.close();
				file.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void main(String[] args) {

		HashSet<MenuItem> list = new HashSet<MenuItem>();
		MenuItem m1 = new MenuItem("sarmale", 30);
		MenuItem m2 = new MenuItem("ciorba de fasole", 20);
		list.add(m1);
		list.add(m2);
		RestaurantSerializator r = new RestaurantSerializator();
		r.serialize(list);
		HashSet<MenuItem> n = r.deserialize();
		for (MenuItem x : n) {
			System.out.println(x.toString());
		}

	}

}
