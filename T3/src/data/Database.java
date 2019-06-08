package data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import business.*;

public class Database {
	
	private Map<Integer, User> users;
	private Map<Integer, Product> products;
	private Map<String,EvaluationGroup> EvaluationGroups;
	
	
	//constantes,dados inicias do database
	final int NumTotalMembers = 10;
	final int NumTotalProducts = 11;
	final List<String> usernames = new ArrayList<>(Arrays.asList("João","Ana","Manoela","Joana","Miguel","Beatriz","Suzana","Natasha","Pedro","Carla"));
	final List<String> states = new ArrayList<>(Arrays.asList("RS","SP","RS","CE","RS","CE","RS","CE","SP","SP"));
	final List<String> ProductCategoryNames = new ArrayList<>(Arrays.asList("BB Cream","CC Cream","DD Cream","Foundation+SPF","Oil Free Matte SPF","Powder Sunscreen"));
	final Map<String,ProductCategory> ProductCategories = new HashMap<>();
	
	final List<Integer> IdsGroupA = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
	final List<Integer> IdsGroupB = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
	final List<Integer> IdsGroupC = new ArrayList<>(Arrays.asList(4, 5, 6, 7, 8, 9, 10));
	
	final String NameGroupA = "SPF A";
	final String NameGroupB = "SPF B";
	final String NameGroupC = "SPF C";
	
	final List<String> ProductsEvaluationGroupList = new ArrayList<>(Arrays.asList(""));
	final List<String> ProductsCategoriesList = new ArrayList<>(Arrays.asList(""));
	final List<Integer> SolicitorIDList = new ArrayList<>(Arrays.asList());
	
	
	
	
	
	public Database () {
		
		
		
		//cria categories
		for(String category : ProductCategoryNames) {
			ProductCategories.put(category, new ProductCategory(category));
		}
		
		//cria usuários
		for(int i=0;i<NumTotalMembers;i++) {
			users.put(i+1,  new User(i+1,usernames.get(i),states.get(i)));
		}
		
		for(int i=0;i<NumTotalProducts;i++) {
		}
		
		
		
		
		addCategoriesToUsers(ProductCategoryNames,ProductCategories);
		
		createEvaluationGroups();
		
		addUsersToEvaluationGroup(IdsGroupA, NameGroupA);
		addUsersToEvaluationGroup(IdsGroupB, NameGroupB);
		addUsersToEvaluationGroup(IdsGroupC, NameGroupC);
		
		
		
		
	}
	
	
	public int getTotalProductCount() {
		return products.size();
	}
	
	
	public Product getProduct(int id) {
		return products.get(id);
	}
	
	public List<Product> getAllProducts(){
		return (List<Product>) products.values();
	}
	
	
	public EvaluationGroup getEvaluationGroup(String name) {
		return EvaluationGroups.get(name);
		
	}
	
	public List<EvaluationGroup> getAllEvaluationGroups(){
		
		return (List<EvaluationGroup>) EvaluationGroups.values();
	}
	
	public User getUser(int id) {
		return users.get(id);
	}
	
	public List<User> getUser(List<Integer> ids){
		List<User> SelectedUsers = new ArrayList<User>();
		
		Iterator<Integer> IdIterator = ids.iterator();
		
		while(IdIterator.hasNext()) {
			SelectedUsers.add(getUser(IdIterator.next()));
		}
		
	
		return SelectedUsers;
	}
	
	public List<User> getAllUsers(){
		return (List<User>) users.values();
	}
	
	private void addCategoriesToUsers(List<String> ProductCategoryNames,Map<String,ProductCategory> ProductCategories) {
		//associa categorias a usuários
				users.get(1).addCategory(ProductCategories.get("BB Cream"));
				users.get(1).addCategory(ProductCategories.get("CC Cream"));
				users.get(1).addCategory(ProductCategories.get("DD Cream"));
				
				users.get(2).addCategory(ProductCategories.get("CC Cream"));
				users.get(2).addCategory(ProductCategories.get("DD Cream"));
				users.get(2).addCategory(ProductCategories.get("Foundation+SPF"));
				
				users.get(3).addCategory(ProductCategories.get("BB Cream"));
				users.get(3).addCategory(ProductCategories.get("Oil Free Matte SPF"));
				
				users.get(4).addCategory(ProductCategories.get("BB Cream"));
				users.get(4).addCategory(ProductCategories.get("CC Cream"));
				users.get(4).addCategory(ProductCategories.get("Foundation+SPF"));
				users.get(4).addCategory(ProductCategories.get("Powder Sunscreen"));
				
				users.get(5).addCategory(ProductCategories.get("Foundation+SPF"));
				users.get(5).addCategory(ProductCategories.get("DD Cream"));
				users.get(5).addCategory(ProductCategories.get("Oil Free Matte SPF"));
				
				
				users.get(6).addCategory(ProductCategories.get("Oil Free Matte SPF"));
				users.get(6).addCategory(ProductCategories.get("CC Cream"));
				users.get(6).addCategory(ProductCategories.get("Powder Sunscreen"));

				users.get(7).addCategory(ProductCategories.get("Powder Sunscreen"));
				users.get(7).addCategory(ProductCategories.get("CC Cream"));
				users.get(7).addCategory(ProductCategories.get("DD Cream"));

				users.get(8).addCategory(ProductCategories.get("BB Cream"));
				users.get(8).addCategory(ProductCategories.get("CC Cream"));
				users.get(8).addCategory(ProductCategories.get("DD Cream"));

				users.get(9).addCategory(ProductCategories.get("Powder Sunscreen"));
				users.get(9).addCategory(ProductCategories.get("Foundation+SPF"));

				users.get(10).addCategory(ProductCategories.get("CC Cream"));
				users.get(10).addCategory(ProductCategories.get("DD Cream"));
				users.get(10).addCategory(ProductCategories.get("Oil Free Matte SPF"));

	}
	
	private void addUsersToEvaluationGroup(List<Integer> UserIDs, String EvaluationGroupName) {
		List<User> UsersToAdd = getUser(UserIDs);
		EvaluationGroup group = getEvaluationGroup(EvaluationGroupName);
		
		for(User user : UsersToAdd) {
			group.addMember(user);
		}
		
		
	}
	
	private void createEvaluationGroups() {
		EvaluationGroups.put(NameGroupA,new EvaluationGroup(NameGroupA));
		EvaluationGroups.put(NameGroupB,new EvaluationGroup(NameGroupB));
		EvaluationGroups.put(NameGroupC,new EvaluationGroup(NameGroupC));
	}

}
