package business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ui.IOUtils;

public class EvaluationGroup {
	
	private String name;
	private Map <Product,List<Evaluation> > evaluations = new HashMap<>();
	private List<User> members = new ArrayList<User>();
	
	private static final boolean SelectAcceptableProducts = true;
	private static final boolean SelectNotAcceptableProducts = !SelectAcceptableProducts;
	
	private static final boolean AscendingOrder = true;
	private static final boolean DescendingOrder = !AscendingOrder;
	
	
	public static final int NameWidth = 10;
	
	
	public EvaluationGroup(String name) {
		this.name = name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void addMember(User NewMember) {
		this.members.add(NewMember);
		
	}
	
	
	
	public boolean evaluationDone() {
		for(List<Evaluation> EvaluationList : evaluations.values()) {
				if(EvaluationList == null) {
					return false;
				}
			
				for(Evaluation evaluation : EvaluationList) {
					
					if(evaluation ==  null || !evaluation.isDone()) {
							return false;}
				
			}
		}
		return true;
	}
	
	
	public void allocate (int numMembers) {
		if(isAllocated() == true || numMembers < User.MinNumberOfEvaluatorsToAllocate || numMembers > User.MaxNumberOfEvaluatorsToAllocate) {
				return;
		}
		
		System.out.println("\nIniciando Alocação\n");
		
		
		
		for(int i = 0; i < numMembers;i++) {
			for(Product ProductAllocate : getOrderedProducts()) {
				
				
				try {
					User evaluator = getOrderedCandidateReviewers(ProductAllocate).get(0);
					
					System.out.println(String.format("Produto ID[%2d] alocado ao Avaliador ID[%2d]", ProductAllocate.getProductID(),evaluator.getID()));
					
					addEvaluation(ProductAllocate, evaluator);
				} 
				
				
				catch (IndexOutOfBoundsException e) {
					System.out.println(String.format("\nNenhum candidato adequado disponível para ser o %2d° avaliador de Produto ID[%2d]",i,ProductAllocate.getProductID()));

				}
				
				
				
				
			}
		}
		
		
		System.out.println("\nFim da Alocação\n");
		
		
		
		
		
	}
	
	public void addEvaluation(Product EvaluatedProduct, Evaluation ExistingEvaluation) {
		List<Evaluation> CurrentEvaluations = evaluations.get(EvaluatedProduct);
		
		try {
			CurrentEvaluations.add(ExistingEvaluation);
			evaluations.put(EvaluatedProduct, CurrentEvaluations);
		}
		
		catch(NullPointerException e) {
			evaluations.put(EvaluatedProduct, new ArrayList<Evaluation>(Arrays.asList(ExistingEvaluation)));
		}
		
	}
	
	public void AddUnallocatedProduct(Product product) {
		
		evaluations.put(product,null);
	}
	
	
	public void addEvaluation(Product EvaluatedProduct, User evaluator) {
		if(EvaluatedProduct == null ||  evaluator == null) {
			return;
		}
		
		
		Evaluation evaluation = new Evaluation(this,EvaluatedProduct,evaluator);
		List<Evaluation> CurrentEvaluations = evaluations.get(EvaluatedProduct);
		
		try {
			CurrentEvaluations.add(evaluation);
			evaluations.put(EvaluatedProduct, CurrentEvaluations);
		}
		
		catch(NullPointerException e) {
			evaluations.put(EvaluatedProduct, new ArrayList<Evaluation>(Arrays.asList(evaluation)));
		}
	}
	
	private List<Product> getOrderedProducts(){
		
		List<Product> ProductsToSort = new ArrayList<Product>(evaluations.keySet());
		
		Collections.sort(ProductsToSort);
		
		return ProductsToSort;
		
	}
	/**
	 * Se o boolean acceptableProducts for {@value #SelectAcceptableProducts}, retorna um mapa de Produto para Nota Média de todos os produtos aceitáveis
	 * <P> Se {@value #SelectNotAcceptableProducts}, retorna o mesmo mapa, mas com os produtos não aceitáveis
	 * */
	private Map<Product,Double> getUnsortedProductAverageScoreMap(boolean acceptableProducts) {
		Map<Product,Double> ProductAverageScoreMap = new HashMap<>();
		
		for(Product CurrentProduct : evaluations.keySet()){
			Double AverageScore = CurrentProduct.getAverageScore();
			
			
			
			if( AverageScore != null && ((acceptableProducts && Product.isAverageScoreAcceptable(AverageScore)) || (!acceptableProducts && !Product.isAverageScoreAcceptable(AverageScore))) ){
				ProductAverageScoreMap.put(CurrentProduct, AverageScore);
			}
		}
		return ProductAverageScoreMap;
		
	}
	
	public List<Product> getAcceptableProducts() {
		
		
		Map<Product,Double> ProductAverageScoreMap = getUnsortedProductAverageScoreMap(SelectAcceptableProducts);
		
		
		ProductComparator AcceptableProductComparator = new ProductComparator(ProductAverageScoreMap,DescendingOrder);
		
		List<Product> AcceptableProductsList = new ArrayList<Product>(ProductAverageScoreMap.keySet());
		
		Collections.sort(AcceptableProductsList, AcceptableProductComparator);
		
		
		
		return AcceptableProductsList;
		
	}
	
	public List<Product> getNotAcceptableProducts() {
		Map<Product,Double> ProductAverageScoreMap = getUnsortedProductAverageScoreMap(SelectNotAcceptableProducts);
		
		
		ProductComparator NotAcceptableProductComparator = new ProductComparator(ProductAverageScoreMap,AscendingOrder);
		
		List<Product> NotAcceptableProductsList = new ArrayList<Product>(ProductAverageScoreMap.keySet());
		
		Collections.sort(NotAcceptableProductsList, NotAcceptableProductComparator);
		return NotAcceptableProductsList;
		
	}
	
	
	
	//private
	private List<User> getOrderedCandidateReviewers(Product EvaluationProduct) {
		 
		 List<User> CandidateReviewers = new ArrayList<User>();
		 
		 UserComparator CandidateReviewersComparator = new UserComparator(this);
		 
		 for(User candidate : members) {
			 if(candidate.canEvaluate(EvaluationProduct)) {
				 CandidateReviewers.add(candidate);
			 }
			 
		 }
		 
		 Collections.sort(CandidateReviewers,CandidateReviewersComparator);
		 
		 
		 
		 return CandidateReviewers;
		 
	}
	
	public boolean isAllocated() {
		
		if(evaluations.isEmpty() || evaluations.values().contains(null)) {
			return false;
		}
		
		else {
			return true;
		}
		
		
	}


	@Override
	public String toString() {
		return String.format("%-" + NameWidth + "s", name);
	}
	
	

}
