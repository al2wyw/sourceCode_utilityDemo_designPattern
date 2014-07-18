package test1;

import org.easymock.EasyMock;
public class EasyMockTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HelloWorld hw = EasyMock.createMock(HelloWorld.class);
		User user = EasyMock.createMock(User.class);
		EasyMock.expect(hw.getUser()).andReturn(user).times(1);
		EasyMock.expect(user.getName()).andReturn("Jack").times(1);
		EasyMock.replay(hw);
		EasyMock.replay(user);
		System.out.println(hw.getUser().getName());
		EasyMock.verify(hw);
	}
}
