package business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class EvaluationGroup {
	
	private String name;
	private Map <Product,List<Evaluation> > evaluations = new HashMap<>();
	private List<User> members = new ArrayList<User>();
	
	
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
		CurrentEvaluations.add(evaluation);
		
		evaluations.put(EvaluatedProduct, CurrentEvaluations);
	}
	
	private List<Product> getOrderedProducts(){
		
		return new ArrayList<Product>(evaluations.keySet());
		
	}
	
	public List<Product> getAcceptableProducts() {
		List<Product> AcceptableProducts = new ArrayList<Product>();
		TreeMap<Double,List<Product>> ProductMap = new TreeMap<Double,List<Product>> (Collections.reverseOrder());
		
		
		Iterator<Product> ProductIterator = evaluations.keySet().iterator();
		
		while(ProductIterator.hasNext()) {
			Product CurrentProduct = ProductIterator.next();
			Double AverageScore = CurrentProduct.getAverageScore();
			
			if(AverageScore != null) {
				AcceptableProducts.add(CurrentProduct);
			
				List <Product> AverageScoreProducts = ProductMap.get(AverageScore);
				
				if(AverageScoreProducts == null) {
					ProductMap.put(AverageScore, new ArrayList<Product>(Arrays.asList(CurrentProduct)));
				}
				
				else {
					AverageScoreProducts.add(CurrentProduct);
					
				}
			}
		
		}
		return AcceptableProducts;
		
	}
	
	public List<Product> getNotAcceptableProducts() {
		List<Product> NotAcceptableProducts = new ArrayList<Product>();
		
		
		
		
		return NotAcceptableProducts;
		
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
