package data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import business.*;

public class Database {
	
	private Map<Integer, User> users;
	private Map<Integer, Product> products;
	private Map<String,EvaluationGroup> EvaluationGroups;
	
	public User getUser(int id) {
		return users.get(id);
	}
	
	public List<User> getUser(List<Integer> ids){
		List<User> SelectedUsers = new ArrayList<User>();
		
		Iterator<Integer> IdIterator = ids.iterator();
		
		while(IdIterator.hasNext()) {
			SelectedUsers.add(getUser(IdIterator.next()));
		}
		
	
		return SelectedUsers;
	}

}
