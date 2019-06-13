package business;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory {
	String name;
	
	public ProductCategory(){
		this.name = null;
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
	
	public boolean isInside(List<ProductCategory> categories) {
		if(categories == null) return false;
		
		
		for(ProductCategory category : categories) {
			if(category.getName().contentEquals(this.getName())){
				return true;
			}
			
		}
		
		return false;
		
		
		
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	
	
	

}
