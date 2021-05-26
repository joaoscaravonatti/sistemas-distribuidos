package socket;

import java.util.ArrayList;
import java.util.HashMap;

public class Database {
	private HashMap<String, ArrayList<String>> lists = new HashMap<String, ArrayList<String>>();
	private String lastValue = null;

	public void addList (String listName) {
		this.lists.put(listName, new ArrayList<String>());
	}

	public String getLists () {
		String value = "\nLISTAS:\n";
		for (String key : this.lists.keySet()) {
			value += " -> " + key + "\n";
		}
		return value;
	}

	public String getListValues (String listName) {
		String value = "\nLISTA " + listName + ":\n";
		ArrayList<String> list = this.getList(listName);
		if (list == null) {
			return value;
		}
		for (String item : list) {
			value += " -> " + item + "\n";
		}
		return value;
	}

	public void addItemToList (String listName, String item) {
		ArrayList<String> list = this.getList(listName);
		if (list == null) {
			return;
		}
		this.getList(listName).add(item);
		this.lastValue = item;
	}

	public void removeItemFromList (String listName, String item) {
		ArrayList<String> list = this.getList(listName);
		if (list == null) {
			return;
		}
		this.getList(listName).remove(item);
	}

	public String getLastValue () {
		return this.lastValue;
	}

	private ArrayList<String> getList (String listName) {
		return this.lists.get(listName);
	}
}
