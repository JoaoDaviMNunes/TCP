package business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product implements Comparable<Product>{
	private static final int BigDecimalScale = 2;
	private static final String ScoreDividerStringValue = "0.00";
	public static final BigDecimal ScoreDivider = new BigDecimal(ScoreDividerStringValue).setScale(BigDecimalScale);
	
	private Integer id;
	private String name;
	private Map <User,Evaluation> evaluations = new HashMap<>();
	
	
	private User solicitor;
	private ProductCategory category;
	
	public EvaluationGroup getGroup() {
		return group;
	}

	public void setGroup(EvaluationGroup group) {
		this.group = group;
	}

	private EvaluationGroup group;
	
	
	/**
	 * Dado certa nota média, retorna se ela é aceitável ou não em comparação ao ScoreDivider.
	 * 
	 * <P>Aceitável >= {@value #ScoreDividerStringValue}
	 * 
	 * <P>Não aceitável < {@value #ScoreDividerStringValue}
	 * */
	public static boolean isAverageScoreAcceptable(Double average) {
		BigDecimal ComparisonAverageScore = BigDecimal.valueOf(average).setScale(BigDecimalScale, RoundingMode.HALF_UP);
		
		return (ComparisonAverageScore.compareTo(ScoreDivider) >= 0);
	};
	
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
	
	public Product(int id, User solicitor , String name,ProductCategory category, EvaluationGroup group) {
		setProductID(id);
		setName(name);
		setSolicitor(solicitor);
		setProductCategory(category);
		setGroup(group);
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
		for(Evaluation evaluation : evaluations.values()) {
			if(evaluation.isDone() == false) {
				return false;
			}
			
		}
		
		return true;
	}
	
	public Double getAverageScore() {
		Number sum = new Double(0.0);
		List<Evaluation> SumEvaluations= new ArrayList<Evaluation>( evaluations.values());
		
		for(Evaluation evaluation : SumEvaluations) {
			sum = sum.doubleValue() +  evaluation.getScore().doubleValue();
		}
		
		
		Number evaluationCount = new Integer(evaluations.values().size());
		
		return sum.doubleValue()/evaluationCount.doubleValue();
	}
	
	public List<User> getEvaluators(){
		
		return new ArrayList<User>(evaluations.keySet());
	}
	
	public Evaluation getEvaluation(User evaluator) {
		return evaluations.get(evaluator);
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
		String buffer = "";
		buffer = buffer.concat("Nome: " + this.name);
		buffer =buffer.concat("\tID: " + this.id);
		buffer = buffer.concat("\tCategoria: "+ this.category);
		buffer = buffer.concat("\tSolicitador: ID " + this.solicitor.getID());
		
		
		
		
		buffer = buffer.concat("\n");
		return buffer;
		
	}
	
	public String toString(boolean verbose) {
		String buffer = "";
		buffer = buffer.concat(this.toString());
		
		if(verbose) {
			for(Map.Entry <User,Evaluation> entry: evaluations.entrySet()) {
				buffer = buffer.concat(String.format("\n Evaluator %s | Score : %d", entry.getKey().getName(), entry.getValue().getScore()));
			}
			
		}
		
		
		return buffer;
		
	}

	public String toString(Double average) {
		String buffer = "";
		buffer = buffer.concat(this.toString());
		buffer = buffer.concat("\nAverage score: " + average);
		return buffer;
		
		
	}
	
	
	
	
	
	
	
	

}
