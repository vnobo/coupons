package com.alex.ali;

import com.alex.ali.core.TaoBaoController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * com.alex.taobao.TaoBaoControllerTest
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/6/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaoBaoControllerTest {

    @Autowired
    private TaoBaoController controller;

    @Test
    public void contexLoads() {
        assertThat(controller).isNotNull();
    }
}
