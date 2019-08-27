import com.utils.LoggerUtils;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/8/27
 * Time: 15:40
 * Desc:
 */

public class MockitoTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Mock
    private ITest test;

    @Test(expected = RuntimeException.class)
    public void test(){
        MockitoAnnotations.initMocks(this);

        when(test.getName(anyInt()))
                .thenReturn("name")
                .thenThrow(new RuntimeException("test"));

        LoggerUtils.getLogger().info(test.getName(1));

        test.getName(2);

    }
}
