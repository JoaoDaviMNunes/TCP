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
		
		System.out.println(IOUtils.generateDivisoryLine());
		System.out.println("Iniciando Alocação");
		System.out.println(IOUtils.generateDivisoryLine());
		
		int LogCount = 0;
		
		for(int i = 0; i < numMembers;i++) {
			for(Product ProductAllocate : getOrderedProducts()) {
				
				
				try {
					User evaluator = getOrderedCandidateReviewers(ProductAllocate).get(0);
					
					System.out.println(String.format("%2d.Produto ID[%2d] alocado ao Avaliador ID[%2d]", LogCount++,ProductAllocate.getProductID(),evaluator.getID()));
					
					addEvaluation(ProductAllocate, evaluator);
				} 
				
				
				catch (IndexOutOfBoundsException e) {
					System.out.println(String.format("%2d.Nenhum candidato adequado disponível para ser o %2d° avaliador de Produto ID[%2d]", LogCount++,i,ProductAllocate.getProductID()));

				}
				
				
				
				
			}
		}
		
		System.out.println(IOUtils.generateDivisoryLine());
		System.out.println("\nFim da Alocação");
		System.out.println(IOUtils.generateDivisoryLine());
		
		
		
		
		
	}
	
	/**
	 * Função auxiliar para inicialização do Database. Associa produtos ao grupo e aloca avaliações a ele.
	 * */
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
	
	
	/**
	 * Função auxiliar para inicialização do Database. Associa produtos com o grupo sem que haja avaliações alocadas a ele.
	 * */
	public void AddUnallocatedProduct(Product product) {
		if(product == null) {
			throw new IllegalArgumentException("\nProduto nulo!");
		}
		
		evaluations.put(product,null);
	}
	
	
	/**
	 * Cria avaliação a partir dos argumentos fornecidos e adiciona ao grupo {@throws IllegalArgumentException} se o produto ou avaliador recebido forem nulos
	 * */
	public void addEvaluation(Product EvaluatedProduct, User evaluator) throws IllegalArgumentException{
		if(EvaluatedProduct == null ||  evaluator == null) {
			throw new IllegalArgumentException("\nProduto:" + EvaluatedProduct + "\nAvaliador:" + evaluator );
		}
		
		Evaluation evaluation = new Evaluation(this,EvaluatedProduct,evaluator);

		try {
			
			List<Evaluation> CurrentEvaluations = evaluations.get(EvaluatedProduct);
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
