package ui.command;

import java.util.List;

import business.EvaluationGroup;
import ui.IOUtils;

public class EvaluationSelectionCommand extends Command{

	public EvaluationSelectionCommand() {
		super("Seleção de avaliações");
	}

	public void execute() {
		System.out.println("\n" + super.name);
		
		List<EvaluationGroup> EvaluationGroupList = super.database.getAllEvaluationGroups();
		String EvaluationGroupListFormatted = IOUtils.printEvaluationGroupList(EvaluationGroupList);
		System.out.println(EvaluationGroupListFormatted);
		
		int EvaluationGroupIndex = IOUtils.readInteger("Selecione o grupo de avaliação", 0, EvaluationGroupList.size()-1, EvaluationGroupListFormatted);
		EvaluationGroup SelectedEvaluationGroup = EvaluationGroupList.get(EvaluationGroupIndex);
		
		System.out.println(SelectedEvaluationGroup);
		
		if(SelectedEvaluationGroup.isAllocated() == false || SelectedEvaluationGroup.evaluationDone() == false) {
			System.out.println("\nGrupo não alocado ou com avaliações pendentes\nSaindo...");
			System.out.println(SelectedEvaluationGroup.isAllocated());
			System.exit(0);
		}
		
	}
}
