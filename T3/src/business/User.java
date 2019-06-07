package business;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User{
	private int id;
	private String name;
	private String StateOfResidence;
	
	private final List<String> BrazilianStates = Arrays.asList("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO");
	
	private Map <EvaluationGroup,List<Evaluation> > evaluations = new HashMap<>();
	
	private List<ProductCategory> InterestCategories = new ArrayList<>();
	
	
	
	public User() {}
	
	public User(int id, String name, String StateOfResidence) {
		setID(id);
		setName(name);
		setStateOfResidence(StateOfResidence);
		
		
	}
	
	public void addEvaluation(Evaluation evaluation) {
		List<Evaluation> CurrentEvaluations = evaluations.get(evaluation.getGroup());
		CurrentEvaluations.add(evaluation);
		evaluations.put(evaluation.getGroup(),CurrentEvaluations);
	}
	
	public int getEvaluationCount(EvaluationGroup group) {
		
		return evaluations.get(group).size();
		
	}
	
	
	
	
	public boolean canEvaluate(Product EvaluationProduct) {
		User solicitor = EvaluationProduct.getSolicitor();
		ProductCategory category = EvaluationProduct.getProductCategory();
		
		return(solicitor.getID() != this.id  &&  !this.StateOfResidence.contentEquals(solicitor.StateOfResidence) && this.InterestCategories.contains(category));
		
	
	}
	
	
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getID() {
		return this.id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void addCategory(ProductCategory NewCategory) {
		this.InterestCategories.add(NewCategory);
	}
	
	public void setStateOfResidence(String StateOfResidence) throws IllegalArgumentException{
		if(BrazilianStates.contains(StateOfResidence.toUpperCase())) {
			this.StateOfResidence = StateOfResidence.toUpperCase();	
		}
		
		else {
			throw new IllegalArgumentException(String.format("Estado %s não existe", StateOfResidence.toUpperCase()));
		}
	}
	
	public String getStateOfResidence() {
		return this.StateOfResidence;
	}

	
	
}

