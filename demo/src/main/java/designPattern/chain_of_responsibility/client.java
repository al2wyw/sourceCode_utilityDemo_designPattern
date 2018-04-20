package designPattern.chain_of_responsibility;

public class client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RequestHandler h1=new RequestHandler();
		RequestHandler h2=new RequestHandler();
		RequestHandler h3=new RequestHandler();
		h1.setHand(h2);
		h2.setHand(h3);
		String cmd="test";
		h1.handle(cmd);
	}

}
