package test.java.com.alex;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Pattern;

/**
 * boot-cool-alliance BearApplicationTest
 * Created by 2019-02-18
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BearApplicationTest {

    @Test
    public void exampleTest() {

        System.out.println(Pattern.matches(
                ".*([￥|$|€]([A-Za-z0-9]{11})[￥|$|€]).*",
                StringUtils.trimToEmpty("【小狮王辛巴婴儿奶瓶ppsu宽口径宝宝奶瓶耐摔带吸管手柄防胀气】https://m.tb.cn/h.3v3IbAN?sm=2a9c7b 点击链接，再选择浏览器咑閞；或復·制这段描述￥fgRfbGfygx3￥后到淘♂寳♀")));
    }

}
