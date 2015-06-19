package test;

public class test_volatile {
	private long a = -1L;
	private String s = "test";
	private int i = 0;
	private volatile boolean flag = true; //just use as a flag to synchronize two threads
	public static void main(String[] args) {
		final test_volatile t = new test_volatile();
		new Thread(){

			public void run() {
				t.test2();
			}
			
		}.start();
		new Thread(){

			public void run() {
				t.test1();
			}
			
		}.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t.setFlag(false);
		System.out.println("done");
	}
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	private void test1(){
		while(flag);
		long t = a;
		String tt = s;
		System.out.println(t);
		System.out.println(tt);
	}
	
	private void test2(){
		while(flag);
		s = "testa";
		a = 1L;
		while(i++<999999999);
	}

}
