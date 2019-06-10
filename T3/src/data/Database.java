package data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import business.*;

public class Database {
	
	private Map<Integer, User> users = new HashMap<>();
	private Map<Integer, Product> products = new HashMap<>();
	private Map<String,EvaluationGroup> EvaluationGroups = new HashMap<>();
	private List<Evaluation> evaluations = new ArrayList<>();
	
	
	//constantes,dados inicias do database
	final int NumTotalMembers = 10;
	final int NumTotalProducts = 11;
	final int NumTotalEvaluations = 12;
	
	
	
	private final Map<String,ProductCategory> ProductCategoriesNameMap = new HashMap<>();
	protected final Map<Integer,List<ProductCategory>> CategoriesIDMap = this.populateCategoriesMap();
	
	final List<Integer> IdsGroupA = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
	final List<Integer> IdsGroupB = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
	final List<Integer> IdsGroupC = new ArrayList<>(Arrays.asList(4, 5, 6, 7, 8, 9, 10));
	
	
	
	final String NameGroupA = "SPF A";
	final String NameGroupB = "SPF B";
	final String NameGroupC = "SPF C";
	
	
	
	
	
	List<DatabaseProduct> DatabaseProductList = DatabaseProduct.initializeDatabaseProductList();
	List<DatabaseEvaluation> DatabaseEvaluationList = DatabaseEvaluation.initializeDatabaseEvaluationList();
	List<DatabaseUser> DatabaseUserList = DatabaseUser.initializeDatabaseUserList(this);
	List<DatabaseEvaluationGroup> DatabaseEvaluationGroupList = DatabaseEvaluationGroup.initializeDatabaseEvaluationGroupList();
	
	
	
	
	public Database () {
		
		
		
		populateUsers();
		populateEvaluationGroups();	
		populateProducts();
		populateEvaluations();
		
		addProductsToUnallocatedEvaluationGroups();
		
		addProductsToUnallocatedEvaluationGroups();
		
	}
	
	
	public int getTotalProductCount() {
		return products.size();
	}
	
	
	public Product getProduct(int id) {
		return products.get(id);
	}
	
	public List<Product> getAllProducts(){
		return new ArrayList<Product>(products.values());
	}
	
	
	public EvaluationGroup getEvaluationGroup(String name) {
		return EvaluationGroups.get(name);
		
	}
	
	public List<EvaluationGroup> getAllEvaluationGroups(){
		
		return new ArrayList<EvaluationGroup>( EvaluationGroups.values());
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
		return new ArrayList<User>(users.values());
	}
	
	
	
	private void addUsersToEvaluationGroup(List<Integer> UserIDs, EvaluationGroup group) {
		List<User> UsersToAdd = getUser(UserIDs);
		
		for(User user : UsersToAdd) {
			group.addMember(user);
		}
		
		
	}
	
	private void addProductsToUnallocatedEvaluationGroups() {
		for(Product UnallocatedProduct : products.values()) {
			if(UnallocatedProduct.getGroup().isAllocated() == false) {
				
				UnallocatedProduct.getGroup().AddUnallocatedProduct(UnallocatedProduct);
				
			}
		}
		
	}
	
	private void addCategoryToCategoryNameMap(ProductCategory category) {
		ProductCategoriesNameMap.put(category.getName(), category);
		
		
	}
	
	private List<ProductCategory> createProductCategoryList(List<String> names){
		List<ProductCategory> ProductCategoryList = new ArrayList<>();
		for(String name : names) {
			
			ProductCategory category = new ProductCategory(name);
			
			ProductCategoryList.add(category);
			addCategoryToCategoryNameMap(category);
			
		}
		return ProductCategoryList;
	}
	
	private void populateUsers() {
		for(DatabaseUser user : DatabaseUserList) {
			users.put(user.UserID, new User(user.UserID,user.name,user.StateOfResidence,user.categories));
		}
		
	}
	
	private void populateEvaluationGroups() {
		for(DatabaseEvaluationGroup DatabaseEvaluationGroupInstance : DatabaseEvaluationGroupList) {
			String GroupName = DatabaseEvaluationGroupInstance.GroupName;
			List<Integer> EvaluatorIDList = DatabaseEvaluationGroupInstance.EvaluatorIDList;
			EvaluationGroup group = new EvaluationGroup(GroupName);
			
			EvaluationGroups.put(GroupName, group);
			addUsersToEvaluationGroup(EvaluatorIDList,group);
			
		}
		
	}


	private void populateProducts() {
		int ProductID=0;
		
		for(DatabaseProduct product : DatabaseProductList) {
					
					User solicitor = getUser(product.SolicitorID);
					EvaluationGroup group = getEvaluationGroup(product.EvaluationGroupName);
					String ProductName = product.ProductName;
					ProductCategory category = ProductCategoriesNameMap.get(product.CategoryName);
					
					products.put( ProductID+1, new Product( ProductID+1, solicitor,  ProductName, category, group));
					
					ProductID++;
				}
	}

	private void populateEvaluations() {
		for(DatabaseEvaluation DatabaseEvaluationInstance : DatabaseEvaluationList) {
			
			User evaluator = getUser(DatabaseEvaluationInstance.EvaluatorID);
			Product product = getProduct(DatabaseEvaluationInstance.ProductID);
			Integer score = DatabaseEvaluationInstance.score;
			EvaluationGroup group = product.getGroup();
			
			evaluations.add( new Evaluation(group,product,evaluator,score));
		}
	}
	
	Map<Integer,List<ProductCategory>> populateCategoriesMap(){
		 Map<Integer,List<ProductCategory>> CategoriesMap = new HashMap<>();
		 
		CategoriesMap.put(1, createProductCategoryList(Arrays.asList("BB Cream","CC Cream","DD Cream")));
		CategoriesMap.put(2, createProductCategoryList(Arrays.asList("Foundation+SPF","CC Cream","DD Cream")));
		CategoriesMap.put(3, createProductCategoryList(Arrays.asList("BB Cream","Oil Free Matte SPF")));
		CategoriesMap.put(4, createProductCategoryList(Arrays.asList("BB Cream","CC Cream","Foundation+SPF","Powder Sunscreen")));
		CategoriesMap.put(5, createProductCategoryList(Arrays.asList("Foundation+SPF","DD Cream","Oil Free Matte SPF")));
		CategoriesMap.put(6, createProductCategoryList(Arrays.asList("Oil Free Matte SPF","CC Cream","Powder Sunscreen")));
		CategoriesMap.put(7, createProductCategoryList(Arrays.asList("Powder Sunscreen","CC Cream","DD Cream")));
		CategoriesMap.put(8, createProductCategoryList(Arrays.asList("BB Cream","CC Cream","DD Cream")));
		CategoriesMap.put(9, createProductCategoryList(Arrays.asList("Powder Sunscreen","Foundation+SPF")));
		CategoriesMap.put(10, createProductCategoryList(Arrays.asList("CC Cream","DD Cream","Oil Free Matte SPF")));

		
		
		
		return CategoriesMap;
	}
	

}






/**
 * Classe para inicializar as avaliações do Database
 * */
class DatabaseEvaluation{
	int ProductID;
	int EvaluatorID;
	Integer score;
	
	DatabaseEvaluation(int productID, int evaluatorID, Integer score) {
		ProductID = productID;
		EvaluatorID = evaluatorID;
		this.score = score;
	}
	
	static List<DatabaseEvaluation> initializeDatabaseEvaluationList(){
		return(
				new ArrayList<>(Arrays.asList(
						new DatabaseEvaluation(1, 8, 2),
						new DatabaseEvaluation(1,10, null),
						new DatabaseEvaluation(2, 7, 2),
						new DatabaseEvaluation(2, 2, 3),
						new DatabaseEvaluation(3, 4, -1),
						new DatabaseEvaluation(3, 6, 1),
						new DatabaseEvaluation(4, 1, 1),
						new DatabaseEvaluation(4,3, 0),
						new DatabaseEvaluation(5, 4,-3),
						new DatabaseEvaluation(5, 5, 3),
						new DatabaseEvaluation(6,3, -1),
						new DatabaseEvaluation(6,6, 0)
						))
			);
		
	}
	
	
}

class DatabaseEvaluationGroup{
	String GroupName;
	List<Integer> EvaluatorIDList;
	
	DatabaseEvaluationGroup(String groupName, List<Integer> evaluatorIDList) {
		GroupName = groupName;
		EvaluatorIDList = evaluatorIDList;
	}
	
	static List<DatabaseEvaluationGroup> initializeDatabaseEvaluationGroupList(){
		return(
				new ArrayList<>(Arrays.asList(
						new DatabaseEvaluationGroup("SPF A",new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7))),
						new DatabaseEvaluationGroup("SPF B",new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7))),
						new DatabaseEvaluationGroup("SPF C",new ArrayList<>(Arrays.asList(4, 5, 6, 7, 8, 9, 10)))
						
						
						))
			);
		
	}
	
}

/**
 * Classe para inicializar os produtos do Database
 * */
class DatabaseProduct{
	int id;
	String ProductName;
	int SolicitorID;
	String EvaluationGroupName;
	String CategoryName;
	
	DatabaseProduct(int id, String name, int solicitorID, String evaluationGroupName, String categoryName) {
		this.id = id;
		this.ProductName = name;
		this.SolicitorID = solicitorID;
		this.EvaluationGroupName = evaluationGroupName;
		this.CategoryName = categoryName;
	}
	
	static List<DatabaseProduct> initializeDatabaseProductList() {
		
		return(
			new ArrayList<>(Arrays.asList(
					new DatabaseProduct(1, "L’oreal DD Cream", 1, "SPF C", "DD Cream"),
					new DatabaseProduct(2, "Avon CC Cream", 6, "SPF B", "CC Cream"),
					new DatabaseProduct(3, "Revolution Powder Sunscreeen", 7, "SPF B", "Powder Sunscreen"),
					new DatabaseProduct(4, "Maybelline BB Cream", 8, "SPF B", "BB Cream"),
					new DatabaseProduct(5, "Revlon Foundation+SPF20", 9, "SPF B", "Foundation+SPF"),
					new DatabaseProduct(6, "Nivea Matte Face SPF", 10, "SPF B", "Oil Free Matte SPF"),
					new DatabaseProduct(7, "La Roche CC Cream", 6, "SPF B", "CC Cream"),
					new DatabaseProduct(8, "Yves Rocher Powder+SPF15", 7, "SPF A", "Powder Sunscreen"),
					new DatabaseProduct(9, "Nivea BB Cream", 8, "SPF A", "BB Cream"),
					new DatabaseProduct(10, "Base O Boticário SPF20", 9, "SPF A", "Foundation+SPF"),
					new DatabaseProduct(11, "Natura SPF20 Rosto Matte", 10, "SPF A", "Oil Free Matte SPF")
					))
		);
		
	}
	
	
}


/**
 * Classe para inicializar os usuários do Database
 * */
class DatabaseUser{
	int UserID;
	String name;
	String  StateOfResidence;
	List<ProductCategory> categories;
	
	DatabaseUser(int userID, String name, String stateOfResidence, List<ProductCategory> categories) {
		UserID = userID;
		this.name = name;
		StateOfResidence = stateOfResidence;
		this.categories = categories;
	}
	
	
	
	
	static List<DatabaseUser> initializeDatabaseUserList(Database database){
		Map<Integer,List<ProductCategory>> CategoriesIDMap = database.CategoriesIDMap;
		
		return(
				new ArrayList<>(Arrays.asList(
						new DatabaseUser(1, "João", "RS", CategoriesIDMap.get(1)),
						new DatabaseUser(2, "Ana", "SP", CategoriesIDMap.get(2)),
						new DatabaseUser(3, "Manoela", "RS", CategoriesIDMap.get(3)),
						new DatabaseUser(4, "Joana", "CE", CategoriesIDMap.get(4)),
						new DatabaseUser(5, "Miguel", "RS", CategoriesIDMap.get(5)),
						new DatabaseUser(6, "Beatriz", "CE", CategoriesIDMap.get(6)),
						new DatabaseUser(7, "Suzana", "RS", CategoriesIDMap.get(7)),
						new DatabaseUser(8, "Natasha", "CE", CategoriesIDMap.get(8)),
						new DatabaseUser(9, "Pedro", "SP", CategoriesIDMap.get(9)),
						new DatabaseUser(10, "Carla", "SP", CategoriesIDMap.get(10))
						
				
						))
			);
		
	}
	
}
