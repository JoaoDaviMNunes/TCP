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
	
	
	
	public static final int ProductNameWidth = 30;
	public static final int CategoryNameWidth = 20;
	public static final int IDWidth = 16;
	public static final int AverageScoreWidth = 14;
	
	
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
		BigDecimal ComparisonAverageScore;
		
		try {
			ComparisonAverageScore = BigDecimal.valueOf(average).setScale(BigDecimalScale, RoundingMode.HALF_UP);
			return (ComparisonAverageScore.compareTo(ScoreDivider) >= 0);
			
		}
		catch(NumberFormatException e) {
			System.out.println("Number format exception:  " + average);
			System.exit(1);
		}
		
		
		return false;
		
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
		
		//this.group.addEvaluation(this,productEvaluation);
		
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
	
	/**
	 * Retorna a nota média de todas as avaliações do produto. 
	 * <p>Se uma ou mais não tiver com nota definida, retorna nulo
	 * */
	public Double getAverageScore() {
		Number sum = new Double(0.0);
		List<Evaluation> SumEvaluations= new ArrayList<Evaluation>( evaluations.values());
		
		for(Evaluation evaluation : SumEvaluations) {
			
			try {
				sum = sum.doubleValue() +  evaluation.getScore().doubleValue();
			} 
			
			catch (NullPointerException e) {
				return null;
			}
		}
		
		
		Number evaluationCount = new Integer(evaluations.values().size());
		
		Double result = sum.doubleValue()/evaluationCount.doubleValue();
		
		if(result.isNaN()) {
			return null;
		}
		
		
		return result;
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
		return Product.isAverageScoreAcceptable(getAverageScore());
		
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
		buffer = buffer.concat(String.format("%-" + ProductNameWidth+ "s  | %-" + IDWidth + "d |  %-" + CategoryNameWidth + "s | %-" + IDWidth + "d |", this.name,this.id,this.category,this.solicitor.getID()));
		
		return buffer;
		
	}
	
	public String toString(boolean verbose) {
		String buffer = "";
		buffer = buffer.concat(this.toString());
		
		if(verbose) {
			buffer = buffer.concat(String.format("%-" + AverageScoreWidth + ".2f|",getAverageScore()));
			
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
