package business;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.*;

public class User{
	private int id;
	private String name;
	private String StateOfResidence;
	
	private final String NamePattern = "^[\\p{L} .'-]+$";
	
	private final List<String> BrazilianStates = Arrays.asList("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO");
	
	private Map <EvaluationGroup,List<Evaluation> > evaluations = new HashMap<>();
	
	private List<ProductCategory> InterestCategories = new ArrayList<>();
	
	
	
	public User() {}
	
	public User(int id, String name, String StateOfResidence) {
		setID(id);
		setName(name);
		setStateOfResidence(StateOfResidence);	
	}
	
	public User(int id, String name, String StateOfResidence,List<ProductCategory> InterestCategories) {
		setID(id);
		setName(name);
		setStateOfResidence(StateOfResidence);
		setInterestCategories(InterestCategories);
		
	}
	
	public void addCategory(ProductCategory NewCategory) {
		this.InterestCategories.add(NewCategory);
	}
	
	public void addEvaluation(Evaluation evaluation) {
		List<Evaluation> CurrentEvaluations = evaluations.get(evaluation.getGroup());
		
		try {
			CurrentEvaluations.add(evaluation);
			evaluations.put(evaluation.getGroup(),CurrentEvaluations);
		}
		
		catch(NullPointerException e) {
			evaluations.put(evaluation.getGroup(), new ArrayList<Evaluation>(Arrays.asList(evaluation)));
		}
		
	}
	
	
	
	
	public boolean canEvaluate(Product EvaluationProduct) {
		User solicitor = EvaluationProduct.getSolicitor();
		ProductCategory category = EvaluationProduct.getProductCategory();
		
		return(solicitor.getID() != this.id  &&  !this.StateOfResidence.contentEquals(solicitor.StateOfResidence) && this.InterestCategories.contains(category));
		
	
	}
	
	
	
	public int getEvaluationCount(EvaluationGroup group) {
		
		return evaluations.get(group).size();
		
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getStateOfResidence() {
		return this.StateOfResidence;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setName(String name) throws IllegalArgumentException{
		Pattern regex = Pattern.compile(NamePattern);
		Matcher matcher = regex.matcher(name);
		
		if(matcher.find()) {
			this.name = name;
		}
		
		else {
			throw new IllegalArgumentException("Nome inválido");
		}
		
		
		
	}
	
	public void setInterestCategories(List<ProductCategory> InterestCategories) {
		this.InterestCategories = InterestCategories;
	}
	
	
	/**
	 * Lança IllegalArgumentException se o estado informado não corresponder a uma sigla válida de estado brasileiro
	 * */
	public void setStateOfResidence(String StateOfResidence) throws IllegalArgumentException{
		if(BrazilianStates.contains(StateOfResidence.toUpperCase())) {
			this.StateOfResidence = StateOfResidence.toUpperCase();	
		}
		
		else {
			throw new IllegalArgumentException(String.format("Estado %s não existe", StateOfResidence.toUpperCase()));
		}
	}
	
	@Override
	public String toString() {
		String buffer = "";
		buffer = buffer.concat(String.format("\nNome: %s | ID: %d | Estado: %s | Categorias de interesse : %s", name,id,StateOfResidence,InterestCategories));
		
		return buffer;
		
	}

	
	
}

