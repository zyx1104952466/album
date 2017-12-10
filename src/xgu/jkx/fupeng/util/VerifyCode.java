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
 * ����ͼ����֤��
 * 
 * @author FuPeng
 * 
 * Jul 19, 2009
 */
public class VerifyCode {
	// ���������
	static Random r = new Random();
	// Ԥ������ַ���
	// static String ssource = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";
	static String source = "abcdefghijklmnopqrstuvwxyz" + "0123456789";
	// ���ַ���ת����Ϊ�ֽ�����
	static char[] src = source.toCharArray();
	// Ԥ�������֤�볤��
	private int codeLength = 4;

	private HttpServletRequest request = null;
	private HttpServletResponse response = null;

	/***************************************************************************
	 * ���캯�� Ĭ�ϵ���֤�볤��ΪcodeLength
	 * 
	 * @param request
	 * @param response
	 ***************************************************************************
	 */
	public VerifyCode(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		//���������֤�룬����ΪĬ��4
		String code = getCode(this.codeLength);  
		//ͨ�����ɺõ���֤���ַ���������֤��ͼƬ
		this.createImage(code);
	}

	/***************************************************************************
	 * ���캯�� ��֤�볤���Լ�����,codeLength
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
		//���������֤�룬����ΪcodeLength
		String code = getCode(this.codeLength);    
		//ͨ�����ɺõ���֤���ַ���������֤��ͼƬ
		this.createImage(code);     
	}

	/**
	 * ��������ַ��� ����Ϊlength
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
	 * ������Χ��������ɫ
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
	 * ���ø÷������õ�����֤������ͼ��
	 * 
	 * @param sCode
	 *            ��֤��
	 * @return
	 */
	private void createImage(String sCode) {

		// ����ContentType����Ϊ image/jpeg
		this.response.setContentType("image/jpeg");
		// ����ҳ�治����
		this.response.setHeader("Pragma", "No-cache");
		this.response.setHeader("Cache-Control", "no-cache");
		this.response.setDateHeader("Expires", 0);
		// ͼ������߶�
		int width = 15 * this.codeLength;
		int height = 20;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		// ��ȡͼ��������
		Graphics g = image.getGraphics();

		// ���������
//		Random random = new Random();

		// �趨����ɫ
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		// �趨����
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

		// ���߿�
		// g.setColor(new Color());
		// g.drawRect(0,0,width-1,height-1)

		// �������155�������ߣ�ʹͼ���е���֤�벻�ױ���������̽�⵽
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
			// ����֤����ʾ��ͼ����
			g.setColor(new Color(20 + r.nextInt(60), 20 + r
					.nextInt(120), 20 + r.nextInt(180)));
			g.drawString(rand, 13 * i + 6, 16);
		}
		//  �ͷŴ�ͼ�ε��������Լ���ʹ�õ�����ϵͳ��Դ
		g.dispose();
		//��֤���ַ����ŵ�session��
System.out.println("��֤�룺"+sCode);
		this.request.getSession().setAttribute("rand", sCode); 

		ServletOutputStream outStream;
		try {
			outStream = response.getOutputStream();
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outStream);
//			encoder.encode(image);
			
			//������ɺ����֤��ͼ��ҳ��
			ImageIO.write(image, "JPEG", outStream);
			outStream.flush();
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}