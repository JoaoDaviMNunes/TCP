package ui.command;

import ui.IOUtils;

import java.util.List;

import business.*;

public class GradeCommand extends Command {

	public GradeCommand() {
		super("Atribuição de notas");
	}
	
	public void execute() {
		System.out.println("\n" + super.name);
		
		
		List<Product> AllProducts = super.database.getAllProducts();
		String ProductListFormatted = IOUtils.printSimpleProductList(AllProducts);
		System.out.println(ProductListFormatted);
		
		
		int SelectedProductIndex = IOUtils.readInteger("Selecione o produto a ser avaliado", 0, super.database.getTotalProductCount()-1, ProductListFormatted);
		Product SelectedProduct = AllProducts.get(SelectedProductIndex);
		
		List<User> SelectedProductEvaluators = SelectedProduct.getEvaluators();
		String EvaluatorListFormatted = IOUtils.printEvaluatorList(SelectedProductEvaluators);
		System.out.println(EvaluatorListFormatted);
		
		int SelectedEvaluatorIndex = IOUtils.readInteger("Selecione o avaliador", 0, SelectedProductEvaluators.size()-1, EvaluatorListFormatted);
		User SelectedEvaluator = SelectedProductEvaluators.get(SelectedEvaluatorIndex);
		
		int score = IOUtils.readInteger("Informe a nota a ser atribuída", Evaluation.minScore, Evaluation.maxScore, null);
		
		Evaluation EvaluationToScore = SelectedProduct.getEvaluation(SelectedEvaluator);
		EvaluationToScore.setScore(score);
		
		
		
		
		
		
	}
}
