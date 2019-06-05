package business;

public class Evaluation {
	
	private Integer score;
	private User evaluator;
	private EvaluationGroup group;
	public final int minScore = -3;
	public final int maxScore = 3;
	
	public Evaluation() {
		score = null;
		
	}
	
	public Evaluation(Integer NewScore) throws IllegalArgumentException{
		setScore(NewScore);
		
	}
	
	public void setScore(Integer newScore ) throws IllegalArgumentException{
		if(newScore.intValue() >= minScore && newScore.intValue() <= maxScore) {
			this.score = newScore;
		}
		
		else throw new IllegalArgumentException(String.format("Notas devem estar entre %d e %d", minScore,maxScore));
	}
	
	public Integer getScore() {
			return this.score;
			
	}
	
	public boolean isDone() {
		return score != null;
	}

}
