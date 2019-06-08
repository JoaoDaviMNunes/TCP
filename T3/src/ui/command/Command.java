package ui.command;

public abstract class Command {
	protected String name;
	
	public Command(String name) {
		this.name = name;
	}
	
	
	public String toString() {
		return this.name;
		
	}
	
	abstract public void execute();
	

}
