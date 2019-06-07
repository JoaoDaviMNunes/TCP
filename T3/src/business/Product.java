package business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Product implements Comparable<Product>{
	private static final int BigDecimalScale = 2;
	public static final BigDecimal ScoreDivider = new BigDecimal("0.00").setScale(BigDecimalScale);
	public static boolean isAverageScoreAcceptable(Double average) {
		BigDecimal ComparisonAverageScore = BigDecimal.valueOf(average).setScale(BigDecimalScale, RoundingMode.HALF_UP);
		
		return (ComparisonAverageScore.compareTo(ScoreDivider) >= 0);
	};
	private Integer id;
	private String name;
	private Map <User,Evaluation> evaluations = new HashMap<>();
	
	
	private User solicitor;
	private ProductCategory category;
	
	private EvaluationGroup group;
	
	public Product() {}
	
	public Product(int id, User solicitor , String name) {
		setProductID(id);
		setName(name);
		setSolicitor(solicitor);
	}
	
	
	public Product(int id, User solicitor , String name,ProductCategory category) {
		setProductID(id);
		setName(name);
		setSolicitor(solicitor);
		setProductCategory(category);
	}
	
	public void addEvaluation(Evaluation productEvaluation) {
		evaluations.put(productEvaluation.getEvaluator() ,productEvaluation);
		
	}
	
	
	
	public void addScore(User evaluator, Integer score) {
		Evaluation toScore = evaluations.get(evaluator);
		
		if(toScore != null) {
			toScore.setScore(score);
		}
		
	}
	
	@Override
	public int compareTo(Product o) {
		Integer id1 = new Integer(this.getProductID());
		Integer id2 = new Integer(o.getProductID());
		
		
		return id1.compareTo(id2);
	}
	
	/**
	 * Verifica se todas as avaliações relacionadas com o produtos possuem nota atribuida. Retorna verdadeiro se sim.
	 * 
	 * */
	public Boolean evaluationDone() {
		if(evaluations.isEmpty()) return false;
		for(Iterator<Evaluation> EvaluationIterator = evaluations.values().iterator();EvaluationIterator.hasNext();) {
			if(EvaluationIterator.next().isDone() == false) {
				return false;
			}
			
		}
		
		return true;
	}
	
	public Double getAverageScore() {
		Number sum = new Double(0.0);
		List<Evaluation> SumProducts = new ArrayList<Evaluation>( evaluations.values());
		
		for(Iterator<Evaluation> SumIterator = SumProducts.iterator(); SumIterator.hasNext();) {
			sum = sum.doubleValue() + SumIterator.next().getScore().doubleValue();
		}
		
		
		Number evaluationCount = new Integer(evaluations.values().size());
		
		return sum.doubleValue()/evaluationCount.doubleValue();
	}
	
	public List<User> getEvaluators(){
		
		return new ArrayList<User>(evaluations.keySet());
	}
	
	public String getName() {
		return this.name;
	}
	
	public ProductCategory getProductCategory() {
		return this.category;
	}
	
	public int getProductID() {
		return this.id.intValue();
	}
	
	public User getSolicitor() {
		return this.solicitor;
	}
	
	public Boolean isAcceptable() {
		return true;
		
	}
	
	
	
	
	
	public void setName(String NewName) {
		this.name = NewName;
	}
	
	
	
	
	

	
	public void setProductCategory(ProductCategory category) {
		this.category = category;
	}
	
	public void setProductID(int newID) {
		this.id = newID;
	}
	
	public void setSolicitor(User solicitor) {
		this.solicitor = solicitor;
		
	}
	
	public String toString() {
		String buffer = "\n-------------------------------------------\n";
		buffer = buffer.concat("Nome: " + this.name);
		buffer =buffer.concat("\tID: " + this.id);
		buffer = buffer.concat("\tCategory: "+ this.category);
		
		for(Map.Entry <User,Evaluation> entry: evaluations.entrySet()) {
			buffer = buffer.concat(String.format("\n Evaluator %s | Score : %d", entry.getKey().getName(), entry.getValue().getScore()));
		}
		
		buffer = buffer.concat("\n");
		return buffer;
		
	}

	public String toString(Double average) {
		String buffer = "";
		buffer = buffer.concat(this.toString());
		buffer = buffer.concat("Average score: " + average);
		return buffer;
		
		
	}
	
	
	
	
	
	
	
	

}
