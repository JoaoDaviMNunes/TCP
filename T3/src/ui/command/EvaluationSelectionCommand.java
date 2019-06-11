package ui.command;

import java.util.List;

import business.EvaluationGroup;
import business.Product;
import data.Database;
import ui.IOUtils;

public class EvaluationSelectionCommand extends Command{

	public EvaluationSelectionCommand(Database database) {
		super("Seleção de avaliações",database);
	}

	public void execute() {
		System.out.println("\n" + super.name);
		
		
		
		List<EvaluationGroup> EvaluationGroupList = super.database.getAllEvaluationGroups();
		String EvaluationGroupListFormatted = IOUtils.generateEvaluationGroupList(EvaluationGroupList);
		System.out.println(EvaluationGroupListFormatted);
		
		
		
		int EvaluationGroupIndex = IOUtils.readInteger("Selecione o grupo de avaliação", 0, EvaluationGroupList.size()-1, EvaluationGroupListFormatted);
		EvaluationGroup SelectedEvaluationGroup = EvaluationGroupList.get(EvaluationGroupIndex);
		
		System.out.println(SelectedEvaluationGroup + "\n");
		
		
		
		if(SelectedEvaluationGroup.isAllocated() == false || SelectedEvaluationGroup.evaluationDone() == false) {
			System.out.println("\nGrupo não alocado ou com avaliações pendentes\nSaindo...");
			System.out.println(SelectedEvaluationGroup.isAllocated());
			System.exit(0);
		}
		
		else {
			List <Product> AcceptableProductList = SelectedEvaluationGroup.getAcceptableProducts();
			List <Product> NotAcceptableProductList = SelectedEvaluationGroup.getNotAcceptableProducts();
			
			
			
			System.out.println("\nProdutos Aceitáveis ");
			System.out.println(IOUtils.printSimpleProductList(AcceptableProductList));
			
			System.out.println("\nProdutos Nâo Aceitáveis ");
			System.out.println(IOUtils.printSimpleProductList(NotAcceptableProductList));
			
		}
		
	}
}
