package business;

import java.util.Map;

public class Product {
	private int id;
	private String name;
	private Map <User,Evaluation> evaluations;
	
	public Product() {}
	
	public Product(int id,String name) {
		setID(id);
		setName(name);
	}
	
	public void addEvaluation(Evaluation productEvaluation, User evaluator) {
		evaluations.put(evaluator, productEvaluation);
		
	}
	
	public void addScore(User evaluator, Integer score) {
		Evaluation toScore = evaluations.get(evaluator);
		
		if(toScore != null) {
			toScore.setScore(score);
		}
		
	}
	
	public int getID() {
		return this.id;
	}
	
	public void setID(int newID) {
		this.id = newID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String NewName) {
		this.name = NewName;
	}
	
	public Double getScoreAverage() {
		Number sum = new Double(0.0);
		Number evaluationCount = new Integer(0);
		
		return sum.doubleValue()/evaluationCount.doubleValue();
	}
	
	
	
	
	
	
	
	

}
