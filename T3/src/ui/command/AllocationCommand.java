package ui.command;

import java.util.List;

import business.EvaluationGroup;
import business.User;
import data.Database;
import ui.IOUtils;

public class AllocationCommand extends Command {
	
	public AllocationCommand (Database database) {
		super("Alocação de produtos",database);
	}
	
	public void execute() {
		System.out.println("\n" + super.name);
		
		List<EvaluationGroup> EvaluationGroupList = super.database.getAllEvaluationGroups();
		String EvaluationGroupListFormatted = IOUtils.generateEvaluationGroupList(EvaluationGroupList);
		System.out.println(EvaluationGroupListFormatted);
		
		
		
		int EvaluationGroupIndex = IOUtils.readInteger("Selecione o grupo de avaliação", 0, EvaluationGroupList.size()-1, EvaluationGroupListFormatted);
		EvaluationGroup AllocatedEvaluationGroup = EvaluationGroupList.get(EvaluationGroupIndex);
		
		
		
		
		int numMembers = IOUtils.readInteger("Informe número de avaliador por produto", User.MinNumberOfEvaluatorsToAllocate, User.MaxNumberOfEvaluatorsToAllocate, null);
		
		AllocatedEvaluationGroup.allocate(numMembers);
		
	}
	

}
