package ui.command;

import data.Database;

public abstract class Command {
	protected String name;
	protected final Database database;
	
	public Command(String name, Database database) {
		this.name = name;
		this.database = database;
	}
	
	
	public String toString() {
		return this.name;
		
	}
	
	abstract public void execute();
	

}
