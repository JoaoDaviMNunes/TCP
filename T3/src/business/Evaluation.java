package business;

public class Evaluation {
	
	private Integer score;
	private User evaluator;
	private EvaluationGroup group;
	private Product EvaluatedProduct;
	static public final int minScore = -3;
	static public final int maxScore = 3;
	
	public Evaluation() {
		score = null;
		
	}
	
	public Evaluation(Integer NewScore) throws IllegalArgumentException{
		setScore(NewScore);
	}
	
	public Evaluation(EvaluationGroup group, Product product, User evaluator) {
		setGroup(group);
		setProduct(product);
		setEvaluator(evaluator);
		score = null;
	}
	
	public Evaluation(EvaluationGroup group, Product product, User evaluator,Integer NewScore) {
		setGroup(group);
		setProduct(product);
		setEvaluator(evaluator);
		setScore(NewScore);
	}
	
	
	
	
	public void setEvaluator(User evaluator) {
		this.evaluator = evaluator;
	}
	
	public User getEvaluator() {
		return this.evaluator;
	}
	
	
	public void setGroup(EvaluationGroup group) {
		this.group = group;
	}
	
	public EvaluationGroup getGroup() {
		return this.group;
	}
	
	public void setProduct(Product product) {
		this.EvaluatedProduct = product;
	}
	
	public Product getProduct() {
		return this.EvaluatedProduct;
	}
	
	/**
	 * Lança IllegalArgumentException se a nota informada não estiver no intervalo válido
	 * <P>Intervalo de notas válidas: {@value #minScore} a {@value #maxScore}
	 * */
	public void setScore(Integer newScore ) throws IllegalArgumentException{
		if(newScore.intValue() >= minScore && newScore.intValue() <= maxScore) {
			this.score = newScore;
		}
		
		else throw new IllegalArgumentException(String.format("Notas devem estar entre %d e %d", minScore,maxScore));
	}
	
	public Integer getScore() {
			return this.score;
			
	}
	
	/**
	 * Retorna verdadeiro se há uma nota não nula associada com a avaliação
	 */
	public boolean isDone() {
		
		return score != null;
	}

}
