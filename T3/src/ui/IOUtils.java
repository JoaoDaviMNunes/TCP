package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import business.*;

public class IOUtils {
	private static final boolean detailed = true;
	private static final boolean simple = !detailed;
	private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	//INPUT METHODS
	public static Integer readInteger(String message, int min, int max, String RepeatMessage) {
		Integer InputValue = null;
		
		while(InputValue == null || InputValue.intValue() < min || InputValue.intValue() > max) {
			System.out.println(String.format("%s [%d a %d]: ", message, min,max));
			try {
				InputValue = new Integer(reader.readLine());
				
				if(InputValue.intValue() < min || InputValue.intValue() > max) {
					System.out.println(String.format("Valor fora de intervalo"));
					System.out.println(RepeatMessage);
					
				}
			}
			
			catch( NumberFormatException e) {
				System.out.println("\nEntrada inválida");
				
			} catch (IOException e) {
				System.out.println("\nIO Exception,saindo...");
				System.exit(-1);
			}
			
		}
		return InputValue;
		
	}
	
	
	
	
	
	
	
	
	
	//OUTPUT METHODS
	public static String printSimpleProductList(List<Product> SimpleProductList) {
		
		return printProductList(SimpleProductList,simple);
	}
	
	public static String printDetailedProductList(List<Product> DetailedProductList) {
		return printProductList(DetailedProductList, detailed);
		
	}
	
	private static String printProductList(List<Product> ProductList,boolean detailed) {
		String buffer = "";
		
		int index = 0;
		for(Product ProductPrint : ProductList) {
			
			buffer = buffer.concat(String.format("\n%d.%s",++index,ProductPrint.toString(detailed)));
		}
		
		return buffer;
		
	}
	
	public static String printEvaluatorList(List<User> Evaluators) {
		String buffer = "";
		
		int index = 0;
		for(User evaluator : Evaluators) {
			buffer = buffer.concat(String.format("\n%d.%s",++index,evaluator.toString()));
		}
		
		return buffer;
		
	}
	
	

}
