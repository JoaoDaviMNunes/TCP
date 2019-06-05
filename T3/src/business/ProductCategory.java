package business;

public class ProductCategory {
	String name;
	
	public ProductCategory(){
		this.name = "INDEFINIDA";
	}
	
	public ProductCategory(String name) {
			this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	
	public boolean compareName(String comparationName) throws NullPointerException{
		
		try {
			return (comparationName.toUpperCase() == this.name.toUpperCase());
		}
		
		catch(NullPointerException e) {
			return false;
		}
		
	}

}
