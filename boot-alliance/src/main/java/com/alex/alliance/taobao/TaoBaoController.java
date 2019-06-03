package com.alex.alliance.taobao;

import cn.hutool.core.img.ImgUtil;
import com.alex.AbstractGenericController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * boot-cool-alliance TbNoticeController
 * Created by 2019-02-15
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@RestController
@RequestMapping("tb")
public class TaoBaoController extends AbstractGenericController {

    @Autowired
    private TaoBaoServer taoBaoServer;

    @RequestMapping("test")
    public Object notice(@RequestParam String id) throws IOException {
        Path rootLocation = Paths.get("D:\\tmp");

        Image source = ImageIO.read(new ClassPathResource("static/zhaomu.jpg").getInputStream());

        Image qrcode = ImageIO.read(new URL( "https://api.weixin.qq.com/cgi-bin/media/get?access_token=19_isohThvDflqys3y_667NYFSeZP8xHCPBbnLx4pEaw69sNrlNpAtOkTQqdc9l_T9vmNaTYiJfa0_LPMS59K-HVlGt_0gZjRZKZGyAL0zFMSv1zhYxThnKkglYjzkMUFiADAIQB&media_id=7cpNMVV9qwVgBzk-kpPDf4K9FiTFcl6mKAOkCjMZsYtA1-Z7vvKYCfAts1JrPyec"));
        qrcode = ImgUtil.scale(qrcode, 208, 208);
        Image image = ImgUtil.pressImage(source, qrcode, 104 + 45, 104 + 75, 1);

        URL url = new URL("http://thirdwx.qlogo.cn/mmopen/vi_32/DFPoBdJAZibFQWHpQsK6ic8t7OvRCO9ibmHPUIvsvFZru42W3HqmR4FCibhtrgkXqLdoh5CiaYZe9U5xg23TkwlxKHA/132");
        Image avater = ImageIO.read(url);
        avater = ImgUtil.scale(avater, 102, 102);
        image = ImgUtil.pressImage(image, avater, 33 - 320 + 51, 51 + 380, 1);


        avater = ImgUtil.scale(avater, 102, 102);
        image = ImgUtil.pressImage(image, avater, 33 - 320 + 51, 51 + 380, 1);

        BufferedImage canvas = new BufferedImage(425, 50, BufferedImage.TYPE_INT_BGR);
        Graphics2D g2d = canvas.createGraphics();
        g2d.setBackground(new Color(248,232 ,121));
        g2d.clearRect(0, 0, 425, 50);

        Font font=new Font("宋体", Font.BOLD + Font.ITALIC, 46);
        // 抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        // 透明度
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1));
        // 在指定坐标绘制水印文字
        final FontMetrics metrics = g2d.getFontMetrics(font);
        final int textHeight = metrics.getAscent() - metrics.getLeading() - metrics.getDescent();
        g2d.drawString("秦客", 2, Math.abs(50 + textHeight) / 2);
        g2d.dispose();

        image =ImgUtil.pressImage(image,canvas, 210-320+213, 50+370, 1);

        ImageIO.write(image, "jpg", rootLocation.resolve("yulogo.jpg").toFile());
        return "成功";
    }
}
