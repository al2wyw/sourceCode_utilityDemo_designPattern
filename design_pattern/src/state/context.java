package state;

public class context {
	private state usersttate;
	private int count;
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public state getUsersttate() {
		return usersttate;
	}

	public void setUsersttate(state usersttate) {
		this.usersttate = usersttate;
	}
	public void action(){//for decoration to add more state
		if(count>3){
			if(!(usersttate instanceof user2))
				usersttate=new user2();
			System.out.println(usersttate);
		}
		usersttate.action();
		count++;
	}
}

class test{
	public static void main(String[] args){
		context test=new context();
		state st=new user1();
		test.setUsersttate(st);
		test.action();
		test.action();
		test.action();
		test.action();
		test.action();
	}
}