package test1;

import org.easymock.EasyMock;
public class EasyMockTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HelloWorld hw = EasyMock.createMock(HelloWorld.class);
		User user = EasyMock.createMock(User.class);
		EasyMock.expect(hw.getUser()).andReturn(user);
		EasyMock.expect(user.getName()).andReturn("Jack");
		EasyMock.replay(hw);
		EasyMock.replay(user);
		System.out.println(hw.getUser().getName());
		
		EasyMock.reset(hw);
		EasyMock.reset(user);
		
		EasyMock.expect(hw.getUser()).andReturn(user);
		EasyMock.expect(user.getName()).andThrow(new RuntimeException());
		EasyMock.replay(hw);
		EasyMock.replay(user);
		System.out.println(hw.getUser().getName());
	}
}
