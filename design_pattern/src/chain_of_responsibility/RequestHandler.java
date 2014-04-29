package chain_of_responsibility;

public class RequestHandler implements handler{
	private handler hand;
	@Override
	public void handle(String cmd) {
		// TODO Auto-generated method stub
		System.out.println("request handler "+cmd);
		if(hand!=null){
			hand.handle(cmd);
		}
	}
	public handler getHand() {
		return hand;
	}
	public void setHand(handler hand) {
		this.hand = hand;
	}
	
}
