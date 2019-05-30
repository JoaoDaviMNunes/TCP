package business;

public class ProductCategory {
	String name;
	
	public ProductCategory(){
		this.name = "INDEFINIDA";
	}
	
	public ProductCategory(String name) throws NullPointerException{
		try {
			this.name = name;
		}
		
		catch( NullPointerException e) {
			this.name = "NULLSTRING";
		}
		
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	
	public boolean compareName(String comparationName) {
		return (comparationName.toUpperCase() == this.name.toUpperCase());
	}

}
