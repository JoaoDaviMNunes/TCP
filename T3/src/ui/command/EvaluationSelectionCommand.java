package ui.command;

public class EvaluationSelectionCommand extends Command{

	public EvaluationSelectionCommand() {
		super("Seleção de avaliações");
	}

	public void execute() {
		System.out.println("\n" + super.name);
	}
}
