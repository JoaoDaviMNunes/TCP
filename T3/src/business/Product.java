package business;

import java.util.HashMap;
import java.util.Map;

public class Product {
	private int id;
	private String name;
	private Map <User,Evaluation> evaluations = new HashMap<>();;
	private User solicitor;
	private ProductCategory category;
	private EvaluationGroup group;
	
	public static final Double ScoreDivider = new Double(0.0);
	
	public Product() {}
	
	public Product(int id, User solicitor , String name) {
		setProductID(id);
		setName(name);
		setSolicitor(solicitor);
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
	
	public User getSolicitor() {
		return this.solicitor;
	}
	
	public void setSolicitor(User solicitor) {
		this.solicitor = solicitor;
		
	}
	
	public void setProductCategory(ProductCategory category) {
		this.category = category;
	}
	
	public ProductCategory getProductCategory() {
		return this.category;
	}
	
	public int getProductID() {
		return this.id;
	}
	
	public void setProductID(int newID) {
		this.id = newID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String NewName) {
		this.name = NewName;
	}
	
	public Double getAverageScore() {
		Number sum = new Double(0.0);
		Number evaluationCount = new Integer(evaluations.values().size());
		
		return sum.doubleValue()/evaluationCount.doubleValue();
	}
	
	public String toString() {
		String buffer = "";
		buffer = buffer.concat("Nome: " + this.name);
		buffer =buffer.concat("ID: " + this.id);
		buffer = buffer.concat("Category: "+ this.category);
		return buffer;
		
	}
	
	public String toString(Double average) {
		String buffer = "";
		buffer = buffer.concat(this.toString());
		buffer = buffer.concat("Average score: " + average);
		return buffer;
		
		
	}
	
	public Boolean isAcceptable() {
		return true;
		
	}
	
	public Boolean evaluationDone() {
		return !evaluations.isEmpty();
	}
	
	
	
	
	
	
	
	

}
