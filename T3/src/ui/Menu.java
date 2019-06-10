package ui;


import java.util.Map;
import java.util.TreeMap;

import data.Database;
import ui.command.*;

public class Menu {
	private static final TreeMap<Integer,Command> MenuMap = createMenuMap();
	private static final int EncerrarPrograma = 0;
	private static final int AllocationIndex = 1;
	private static final int EvaluationSelectionIndex = 2;
	private static final int GradeIndex = 3;
	
	private static  TreeMap<Integer,Command> createMenuMap(){
		
		TreeMap<Integer,Command> CreatedMap = new TreeMap<Integer,Command>();
		
		Database database = new Database();
		
		CreatedMap.put(AllocationIndex, new AllocationCommand(database));
		CreatedMap.put(EvaluationSelectionIndex, new EvaluationSelectionCommand(database));
		CreatedMap.put(GradeIndex, new GradeCommand(database));
		CreatedMap.put(EncerrarPrograma,new ExitCommand());
		
		return CreatedMap;
		
	}
	
	public static void main(String[] args) throws Exception {
		
		while(true) {
			
		System.out.println(showMenuOptions(MenuMap));
		Command SelectedCommand = MenuMap.get(IOUtils.readInteger("Selecione a opção desejada",MenuMap.firstKey(), MenuMap.lastKey(),showMenuOptions(MenuMap)));
		SelectedCommand.execute();
		
		}
		
	}
	
	static private String showMenuOptions(Map<Integer,Command> MenuMap) {
		String buffer = "";
		buffer = buffer.concat(IOUtils.generateDivisoryLine() + "\n");
		
		for(Map.Entry <Integer,Command> entry: MenuMap.entrySet()) {
			buffer = buffer.concat(String.format("\n%d.%s", entry.getKey(),entry.getValue().toString()));
			
		}
		
		buffer = buffer.concat(IOUtils.generateDivisoryLine() + "\n");
		return buffer;
	}
	
	

}
