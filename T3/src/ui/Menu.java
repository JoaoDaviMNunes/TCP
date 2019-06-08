package ui;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import business.Evaluation;
import business.User;
import ui.command.*;

public class Menu {
	private static final TreeMap<Integer,Command> MenuMap = createMenuMap();
	private static final int AllocationIndex = 1;
	private static final int EvaluationSelectionIndex = 2;
	private static final int GradeIndex = 3;
	
	private static  TreeMap<Integer,Command> createMenuMap(){
		TreeMap<Integer,Command> CreatedMap = new TreeMap<Integer,Command>();
		CreatedMap.put(AllocationIndex, new AllocationCommand());
		CreatedMap.put(EvaluationSelectionIndex, new EvaluationSelectionCommand());
		CreatedMap.put(GradeIndex, new GradeCommand());
		
		return (TreeMap<Integer, Command>) Collections.unmodifiableMap(CreatedMap);
		
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(showMenuOptions(MenuMap));
		Command SelectedCommand = MenuMap.get(IOUtils.readInteger("Selecione a opção desejada",MenuMap.firstKey(), MenuMap.lastKey(),showMenuOptions(MenuMap)));
		SelectedCommand.execute();
		
	}
	
	static private String showMenuOptions(Map<Integer,Command> MenuMap) {
		String buffer = "";
		buffer = buffer.concat("\n_____________________________________\n");
		for(Map.Entry <Integer,Command> entry: MenuMap.entrySet()) {
			buffer = buffer.concat(String.format("\n%d.%s", entry.getKey(),entry.getValue().toString()));
			
		}
		return buffer;
	}
	
	

}
