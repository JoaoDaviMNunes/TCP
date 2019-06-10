package ui.command;

import business.User;
import data.Database;
import ui.IOUtils;

public class AllocationCommand extends Command {
	
	public AllocationCommand (Database database) {
		super("Alocação de produtos",database);
	}
	
	public void execute() {
		System.out.println("\n" + super.name);
		
		String EvaluationGroupName = IOUtils.readString("\nInforme nome do grupo a ser alocado: ");
		int numMembers = IOUtils.readInteger("Informe número de avaliador por produto", User.MinNumberOfEvaluatorsToAllocate, User.MaxNumberOfEvaluatorsToAllocate, null);
		
		super.database.getEvaluationGroup( EvaluationGroupName).allocate(numMembers);
		
	}
	

}
