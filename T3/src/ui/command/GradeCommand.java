package ui.command;

public class GradeCommand extends Command {

	public GradeCommand() {
		super("Atribuição de notas");
	}
	
	public void execute() {
		System.out.println("\n" + super.name);
	}
}
