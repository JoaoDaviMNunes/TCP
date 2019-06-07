package business;

import java.util.Comparator;

public class UserComparator implements Comparator<User> {
	private final EvaluationGroup group;
	
	public UserComparator(EvaluationGroup group) {
		this.group = group;
	}
	@Override
	public int compare(User user1, User user2) {
		Integer EvaluationCount1 = new Integer(user1.getEvaluationCount(group));
		Integer EvaluationCount2 = new Integer(user2.getEvaluationCount(group));
		
		if(EvaluationCount1 == EvaluationCount2) {
			Integer id1 = new Integer(user1.getID());
			Integer id2 = new Integer(user2.getID());
			
			return(id1.compareTo(id2));
		}
		
		else {
			return EvaluationCount1.compareTo(EvaluationCount2);
		}
	}

}
