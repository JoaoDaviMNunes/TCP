package ui.command;

public class AllocationCommand extends Command {
	
	public AllocationCommand () {
		super("Alocação de produtos");
	}
	
	public void execute() {
		System.out.println("\n" + super.name);
		
	}
	

}
