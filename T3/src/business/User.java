package business;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.*;

public class User{
	private Integer id;
	private String name;
	private String StateOfResidence;
	
	private final String NamePattern = "^[\\p{L} .'-]+$";
	
	private final List<String> BrazilianStates = Arrays.asList("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO");
	
	private Map <EvaluationGroup,List<Evaluation> > evaluations = new HashMap<>();
	
	private List<ProductCategory> InterestCategories = new ArrayList<>();
	
	public static final int UserNameWidth = 20;
	public static final int StateNameWidth = 7;
	public static final int IDWidth = 4;
	public static final int CategoryListWidth = 30;
	
	public static final int MinNumberOfEvaluatorsToAllocate = 2;
	public static final int MaxNumberOfEvaluatorsToAllocate = 5;
	
	
	
	public User() {}
	
	public User(int it) {
		setID(id);
		setName(null);
		setStateOfResidence(null);
		setInterestCategories(null);
	}
	
	public User(int id, String name, String StateOfResidence) {
		setID(id);
		setName(name);
		setStateOfResidence(StateOfResidence);	
		setInterestCategories(null);
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
		ProductCategory EvaluationProductCategory = EvaluationProduct.getProductCategory();
		
		
		boolean UserHasNotAlreadyEvaluatedProduct = EvaluationProduct.getEvaluation(this) == null;
		boolean UserIsNotSolicitor = solicitor.getID() != this.id;
		boolean UserLivesDifferenteStateThanSolicitor = !this.StateOfResidence.contentEquals(solicitor.StateOfResidence);
		
		
		boolean UserInterestCategoriesMatchProducts = false;
		for(ProductCategory category : this.InterestCategories) {
			if(category.getName().contentEquals(EvaluationProductCategory.getName())){
				UserInterestCategoriesMatchProducts = true;
			}
			
		}
		
		
		return(UserIsNotSolicitor && UserHasNotAlreadyEvaluatedProduct && UserLivesDifferenteStateThanSolicitor 
				&& UserInterestCategoriesMatchProducts);
		
	
	}
	
	
	
	public int getEvaluationCount(EvaluationGroup group) {
		
		try {
			int EvaluationCount = evaluations.get(group).size();
			return EvaluationCount;
		} 
		
		catch (NullPointerException e) {
			return 0;
		}
		
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
		if(name == null) {
			System.out.println(String.format("Avaliador id[%d] recebeu nome nulo",this.id));
			this.name = null;
			return;
		}
		
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
		if(StateOfResidence == null) {
			
			System.out.println(String.format("Avaliador id[%d] recebeu estado nulo",this.id));
			this.StateOfResidence = null;
			return;
		}
		
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
		buffer = buffer.concat(String.format("%-" + UserNameWidth + "s | %-" + IDWidth + "d | %-" + StateNameWidth + "s | %-" + CategoryListWidth + "s", name,id,StateOfResidence,InterestCategories));
		
		return buffer;
		
	}

	
	
}

