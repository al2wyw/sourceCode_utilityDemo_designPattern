package command;


public class Invoker {  
      
    private Command command;  
      
    public Invoker(Command command) {  
        this.command = command;  
    }  
  
    public void action(){  
        command.exe();  
    }
    
    public void undo(){
    	command.undo();
    }
	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}  
    
}

