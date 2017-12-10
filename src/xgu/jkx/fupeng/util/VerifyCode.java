package xgu.jkx.fupeng.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 生成图形验证码
 * 
 * @author FuPeng
 * 
 * Jul 19, 2009
 */
public class VerifyCode {
	// 随机数对象
	static Random r = new Random();
	// 预定义的字符串
	// static String ssource = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";
	static String source = "abcdefghijklmnopqrstuvwxyz" + "0123456789";
	// 把字符串转换成为字节数组
	static char[] src = source.toCharArray();
	// 预定义的验证码长度
	private int codeLength = 4;

	private HttpServletRequest request = null;
	private HttpServletResponse response = null;

	/***************************************************************************
	 * 构造函数 默认的验证码长度为codeLength
	 * 
	 * @param request
	 * @param response
	 ***************************************************************************
	 */
	public VerifyCode(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		//产生随机验证码，长度为默认4
		String code = getCode(this.codeLength);  
		//通过生成好的验证码字符串生成验证码图片
		this.createImage(code);
	}

	/***************************************************************************
	 * 构造函数 验证码长度自己设置,codeLength
	 * 
	 * @param request
	 * @param response
	 * @param codeLength
	 */
	public VerifyCode(HttpServletRequest request, HttpServletResponse response,
			Integer codeLength) {
		this.codeLength = codeLength.intValue();
		this.request = request;
		this.response = response;
		//产生随机验证码，长度为codeLength
		String code = getCode(this.codeLength);    
		//通过生成好的验证码字符串生成验证码图片
		this.createImage(code);     
	}

	/**
	 * 产生随机字符串 长度为length
	 * 
	 * @param length
	 * @return
	 */
	private String getCode(int length) {
		char[] buf = new char[length];
		int rnd;
		for (int i = 0; i < length; i++) {
			rnd = Math.abs(r.nextInt()) % src.length;

			buf[i] = src[rnd];
		}
		return new String(buf);
	}

	/**
	 * 给定范围获得随机颜色
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * 调用该方法将得到的验证码生成图象
	 * 
	 * @param sCode
	 *            验证码
	 * @return
	 */
	private void createImage(String sCode) {

		// 设置ContentType类型为 image/jpeg
		this.response.setContentType("image/jpeg");
		// 设置页面不缓存
		this.response.setHeader("Pragma", "No-cache");
		this.response.setHeader("Cache-Control", "no-cache");
		this.response.setDateHeader("Expires", 0);
		// 图象宽度与高度
		int width = 15 * this.codeLength;
		int height = 20;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		// 获取图形上下文
		Graphics g = image.getGraphics();

		// 生成随机类
//		Random random = new Random();

		// 设定背景色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		// 设定字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

		// 画边框
		// g.setColor(new Color());
		// g.drawRect(0,0,width-1,height-1)

		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = r.nextInt(width);
			int y = r.nextInt(height);
			int xl = r.nextInt(12);
			int yl = r.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		for (int i = 0; i < this.codeLength; i++) {
			String rand = sCode.substring(i, i + 1);
			// 将认证码显示到图象中
			g.setColor(new Color(20 + r.nextInt(60), 20 + r
					.nextInt(120), 20 + r.nextInt(180)));
			g.drawString(rand, 13 * i + 6, 16);
		}
		//  释放此图形的上下文以及它使用的所有系统资源
		g.dispose();
		//验证码字符串放到session中
System.out.println("验证码："+sCode);
		this.request.getSession().setAttribute("rand", sCode); 

		ServletOutputStream outStream;
		try {
			outStream = response.getOutputStream();
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outStream);
//			encoder.encode(image);
			
			//输出生成后的验证码图像到页面
			ImageIO.write(image, "JPEG", outStream);
			outStream.flush();
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}