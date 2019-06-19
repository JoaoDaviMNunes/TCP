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
		
		if(min > max) {
			System.out.println("Limite inferior maior que superior");
			return null;
		}
		
		
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
	public static void printEvaluationGroups(List<EvaluationGroup> EvaluationGroups) {
		
		String EvaluationGroupListFormatted = IOUtils.generateEvaluationGroupList(EvaluationGroups);
		System.out.println(EvaluationGroupListFormatted);
	}
	
	
	public static String generateEvaluationGroupList(List <EvaluationGroup> EvaluationGroupList) {
		String buffer = "";
		
		int index = 0;
		
		buffer = buffer.concat(generateDivisoryLine());
		
		buffer = buffer.concat(String.format("\n| No | %-" + EvaluationGroup.NameWidth + "s", "Nome do grupo"));
		
		buffer = buffer.concat(generateDivisoryLine());
		
		
		for(EvaluationGroup PrintGroup : EvaluationGroupList) {
			buffer = buffer.concat(String.format("\n|%2d  |%s", index++,PrintGroup.toString()));
		}
		
		
		buffer = buffer.concat(generateDivisoryLine());
		return buffer;
	}
	
	
	public static String generateSimpleProductList(List<Product> SimpleProductList) {
		
		return generateProductList(SimpleProductList,simple);
	}
	
	public static String generateDetailedProductList(List<Product> DetailedProductList) {
		return generateProductList(DetailedProductList, detailed);
		
	}
	
	private static String generateProductList(List<Product> ProductList,boolean detailed) {
		String buffer = "";
		
		int index = 0;
		int ExtraWidth = 0;
		
		if(detailed) ExtraWidth = Product.AverageScoreWidth;
		
		
	
		
		buffer = buffer.concat(generateDivisoryLine(ExtraWidth));
		
		
		String ProductNameColumn = String.format("%-" + Product.ProductNameWidth+ "s", "Nome Produto");
		String ProductIDColumn = String.format("%-" + + Product.IDWidth + "s" , "ID Produto");
		String ProductCategoryColumn = String.format("%-" + + Product.CategoryNameWidth  + "s" , "Categoria");
		String SolicitorIDColumn = String.format("%-" + + Product.IDWidth + "s" , "ID Solicitador");
		
		buffer = buffer.concat(String.format("\n|No | %s | %s |  %s | %s |", ProductNameColumn,ProductIDColumn,ProductCategoryColumn,SolicitorIDColumn));

	
		
		if(detailed) {
			buffer = buffer.concat(String.format("%-" + Product.AverageScoreWidth + "s|" ,"Nota media"));
			
		}
		
		buffer = buffer.concat(generateDivisoryLine(ExtraWidth));
		
		
		
		for(Product ProductPrint : ProductList) {
			
			buffer = buffer.concat(String.format("\n| %-2d|%s",index++,ProductPrint.toString(detailed)));
			buffer = buffer.concat(generateDivisoryLine(ExtraWidth));
		}
		
		return buffer;
		
	}
	
	private static String generateDivisoryLine(int ExtraWidth) {
		String buffer = "\n";
		final int ColumnsDivisoriesWdith = 17;
		int TotalWidth = ExtraWidth + ColumnsDivisoriesWdith + Product.ProductNameWidth + Product.CategoryNameWidth + Product.IDWidth*2;
		
		for(int i=0;i<=TotalWidth;i++) {
			buffer = buffer.concat("_");
		}
		
		return buffer;
	}
	
	public static String generateDivisoryLine() {
		return generateDivisoryLine(0);
		
	}
	
	public static void printMessageWithDivisoryLines(String message) {
		System.out.println(IOUtils.generateDivisoryLine());
		System.out.println(message);
		System.out.println(IOUtils.generateDivisoryLine());
		
	}
	
	public static String generateEvaluatorList(List<User> Evaluators) {
		String buffer = "\n";
		
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
