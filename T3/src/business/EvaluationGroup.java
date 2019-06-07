package business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class EvaluationGroup {
	
	private String name;
	private Map <Product,List<Evaluation> > evaluations = new HashMap<>();
	private List<User> members = new ArrayList<User>();
	
	
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
	
	public void allocate (int numMembers) {
		
	}
	
	public void addEvaluation(Product EvaluatedProduct, User evaluator) {
		Evaluation evaluation = new Evaluation(this,EvaluatedProduct,evaluator);
		List<Evaluation> CurrentEvaluations = evaluations.get(EvaluatedProduct);
		if(CurrentEvaluations == null) {
			evaluations.put(EvaluatedProduct, new ArrayList<Evaluation>(Arrays.asList(evaluation)));
		}
		
		else {
			CurrentEvaluations.add(evaluation);	
			evaluations.put(EvaluatedProduct, CurrentEvaluations);
		}
	}
	
	private List<Product> getOrderedProducts(){
		
		return new ArrayList<Product>(evaluations.keySet());
		
	}
	
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
		
		Map<Product,Double> ProductAverageScoreMap = getUnsortedProductAverageScoreMap(true);
		LinkedHashMap<Product,Double>  ProductAverageScoreSorted = new LinkedHashMap<>();
		
		ProductAverageScoreMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(entry -> ProductAverageScoreSorted.put(entry.getKey(), entry.getValue()));
		
		
		return new ArrayList<Product>(ProductAverageScoreSorted.keySet());
		
	}
	
	public List<Product> getNotAcceptableProducts() {
		Map<Product,Double> ProductAverageScoreMap = getUnsortedProductAverageScoreMap(false);
		LinkedHashMap<Product,Double>  ProductAverageScoreSorted = new LinkedHashMap<>();
		
		ProductAverageScoreMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(entry -> ProductAverageScoreSorted.put(entry.getKey(), entry.getValue()));
		
		return new ArrayList<Product>(ProductAverageScoreSorted.keySet());
		
	}
	
	public boolean isAllocated() {
		return (!evaluations.isEmpty()) ;
	}
	
	//private
	public List<User> getOrderedCandidateReviewers(Product EvaluationProduct) {
		 Iterator<User> MembersIterator = members.iterator();
		 List<User> OrderedCandidateReviewers = new ArrayList<User>();
		 
		 
		 TreeMap<Integer,List<User>> CandidateReviewers = new TreeMap<>();
		 
		 while(MembersIterator.hasNext()) {
			 User member = MembersIterator.next();
			 
			 int EvaluationCounter = member.getEvaluationCount(this);
			 
			 List<User> reviewers = CandidateReviewers.get(EvaluationCounter);
			
			 
			 if(reviewers == null) {
				 CandidateReviewers.put(EvaluationCounter,new ArrayList<User>(Arrays.asList(member)));
				 
			 }
			 
			 else {
				 reviewers.add(member);
			 }
		 }
		 
		 for(Map.Entry<Integer,List<User>> entry: CandidateReviewers.entrySet()) {
			 
			 if(!OrderedCandidateReviewers.isEmpty()) {
				 OrderedCandidateReviewers.addAll(entry.getValue());
			 }
			 
			 else {
				 OrderedCandidateReviewers = entry.getValue();
			 }
			 
			 
			 
		 }
		 
		 
		 
		 return OrderedCandidateReviewers;
		 
	}
	
	

}
