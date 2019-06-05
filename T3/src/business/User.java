package business;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class User {
	private int id;
	private String name;
	private String StateOfResidence;
	
	private final List<String> BrazilianStates = Arrays.asList("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO");
	
	Map <EvaluationGroup,List<Evaluation> > evaluations;
	
	
	public User() {}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setStateOfResidence(String StateOfResidence) throws IllegalArgumentException{
		if(BrazilianStates.contains(StateOfResidence.toUpperCase())) {
			this.StateOfResidence = StateOfResidence.toUpperCase();	
		}
		
		else {
			throw new IllegalArgumentException("Estado não existe");
		}
	}
	

}
