package business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EvaluationGroup {
	
	private String name;
	private Map <Product,List<Evaluation> > evaluations = new HashMap<>();
	private List<User> members = new ArrayList<User>();
	
	private static final boolean SelectAcceptableProducts = true;
	private static final boolean SelectNotAcceptableProducts = !SelectAcceptableProducts;
	
	private static final boolean AscendingOrder = true;
	private static final boolean DescendingOrder = !AscendingOrder;
	
	
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
		for(Iterator<List<Evaluation>> EvaluationListIterator = evaluations.values().iterator(); EvaluationListIterator.hasNext();) {
			
				for(Iterator<Evaluation> EvaluationIterator = EvaluationListIterator.next().iterator();EvaluationIterator.hasNext();) {
					
					if(!EvaluationIterator.next().isDone()) {
							return false;
					}
				
			}
			
			
		}
		
		return true;
	}
	
	
	public void allocate (int numMembers) {
		System.out.println("Sorted by id\n");
		System.out.println(getOrderedProducts());
		System.out.println("\n______________________");
		
		
		
		
		
	}
	
	public void addEvaluation(Product EvaluatedProduct, User evaluator) {
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
		
		for(Iterator<Product> ProductIterator = evaluations.keySet().iterator();ProductIterator.hasNext();) {
			Product CurrentProduct = ProductIterator.next();
			Double AverageScore = CurrentProduct.getAverageScore();
			
			if((acceptableProducts && Product.isAverageScoreAcceptable(AverageScore)) || (!acceptableProducts && !Product.isAverageScoreAcceptable(AverageScore)) ){
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
		 
		 for(Iterator<User> MembersIterator = members.iterator();MembersIterator.hasNext();) {
			 CandidateReviewers.add(MembersIterator.next());
		 }
		 
		 Collections.sort(CandidateReviewers,CandidateReviewersComparator);
		 
		 
		 
		 return CandidateReviewers;
		 
	}
	
	public boolean isAllocated() {
		return (!evaluations.isEmpty()) ;
	}
	
	

}
