package business;

import java.util.Comparator;
import java.util.Map;

public class ProductComparator implements Comparator<Product> {
	private final Map<Product,Double> ProductAverageScoreMap;
	private final boolean AscendingOrder;
	
	
	
	public ProductComparator(Map<Product,Double> ProductAverageScoreMap, boolean AscendingOrder) {
		this.ProductAverageScoreMap = ProductAverageScoreMap;
		this.AscendingOrder = AscendingOrder;
	}
	
	
	@Override
	public int compare(Product product1, Product product2) {
		
		if(AscendingOrder) {
			return ProductAverageScoreMap.get(product1).compareTo(ProductAverageScoreMap.get(product2));
		}
		
		else {
			return ProductAverageScoreMap.get(product2).compareTo(ProductAverageScoreMap.get(product1));
		}
		
		
	}

}
