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
	
	/**
	 * Lê um número inteiro até que um valor dentro do intervalo seja informado.
	 * <p> Exibe RepeatMessage caso o valor esteja fora do intervalo informado
	 * */
	public static Integer readInteger(String message, int min, int max, String RepeatMessage) {
		Integer InputValue = null;
		
		while(InputValue == null || InputValue.intValue() < min || InputValue.intValue() > max) {
			System.out.println(String.format("%s [%d a %d]: ", message, min,max));
			try {
				InputValue = new Integer(reader.readLine());
				
				if(InputValue.intValue() < min || InputValue.intValue() > max) {
					System.out.println(String.format("Valor fora de intervalo"));
					
					
					if(RepeatMessage != null)	System.out.println(RepeatMessage);
					
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
	
	public static String readString(String message) {
		String InputString = null;
		
		System.out.println(message);
		
		try {
			InputString = reader.readLine();
		} catch (IOException e) {
			System.out.println("\nIO Exception,saindo...");
			System.exit(-1);
		}
		
		return InputString;
	}
	
	
	
	
	
	
	
	
	//OUTPUT METHODS
	public static String printEvaluationGroupList(List <EvaluationGroup> EvaluationGroupList) {
		String buffer = "";
		
		int index = 0;
		buffer = buffer.concat(String.format("\n   %-" + EvaluationGroup.NameWidth + "s\n", "Nome do grupo"));
		
		for(EvaluationGroup PrintGroup : EvaluationGroupList) {
			buffer = buffer.concat(String.format("\n%2d.%s", index++,PrintGroup.toString()));
		}
		
		return buffer;
	}
	
	
	public static String printSimpleProductList(List<Product> SimpleProductList) {
		
		return printProductList(SimpleProductList,simple);
	}
	
	public static String printDetailedProductList(List<Product> DetailedProductList) {
		return printProductList(DetailedProductList, detailed);
		
	}
	
	private static String printProductList(List<Product> ProductList,boolean detailed) {
		String buffer = "";
		
		int index = 0;
		
		buffer = buffer.concat(generateDivisoryLine());
		buffer = buffer.concat(String.format("\n|No |%-" + Product.ProductNameWidth+ "s  | %-" + Product.IDWidth + "s |  %-" + Product.CategoryNameWidth + "s | %-" + Product.IDWidth + "s |", "Nome Produto","ID Produto","Categoria","ID Solicitador"));

		
		
		buffer = buffer.concat(generateDivisoryLine());
		
		
		
		for(Product ProductPrint : ProductList) {
			
			buffer = buffer.concat(String.format("\n| %-2d|%s",index++,ProductPrint.toString(detailed)));
			buffer = buffer.concat(generateDivisoryLine());
		}
		
		return buffer;
		
	}
	
	public static String generateDivisoryLine() {
		String buffer = "\n";
		int TotalWidth = 17 + Product.ProductNameWidth + Product.CategoryNameWidth + Product.IDWidth*2;
		
		for(int i=0;i<TotalWidth;i++) {
			buffer = buffer.concat("_");
		}
		
		return buffer;
		
	}
	
	public static String printEvaluatorList(List<User> Evaluators) {
		String buffer = "";
		
		int index = 0;
		buffer = buffer.concat(generateDivisoryLine());
		
		buffer = buffer.concat(String.format("\n| No |%-" + User.UserNameWidth + "s | %-" + User.IDWidth + "s | %-" + User.StateNameWidth + "s | %-" + User.CategoryListWidth + "s", "Nome Usuário", "ID","Estado","Categorias Interesse"));
		
		buffer = buffer.concat(generateDivisoryLine());
		
		for(User evaluator : Evaluators) {
			buffer = buffer.concat(String.format("\n| %-2d |%s",index++,evaluator.toString()));
			buffer = buffer.concat(generateDivisoryLine());
		}
		
		return buffer;
		
	}
	
	

}
