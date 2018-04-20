package designPattern.command;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

				Receiver object=new Receiver();
				MyCommand com1=new MyCommand(object);
				MyCommand com2=new MyCommand(object);
				Invoker control=new Invoker(com1);
				System.out.println(object.getValue());
				control.action();
				System.out.println(object.getValue());
				control.undo();
				System.out.println(object.getValue());
				control.action();
				System.out.println(object.getValue());
				control.action();
				System.out.println(object.getValue());
				control.action();
				System.out.println(object.getValue());
				control.undo();
				System.out.println(object.getValue());
			}
}
