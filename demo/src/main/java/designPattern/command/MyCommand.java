package designPattern.command;


public class MyCommand implements Command {  
  
    private Receiver receiver;  
      
    public MyCommand(Receiver receiver) {  
        this.receiver = receiver;  
    }  
  
    @Override  
    public void exe() {  
    	receiver.save();
        receiver.action();  
    }  
    public void undo(){
    	receiver.restore();
    }
}  

