package xgu.jkx.fupeng.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import xgu.jkx.fupeng.exception.NullSourceException;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiWriter;
import com.sun.jimi.core.options.JPGOptions;

/**
 * ͼƬ������
 * 
 * @author FuPeng
 * 
 * Jul 15, 2009
 */
public class ImageZoom {
	
	/**
	 * ����ģ����ݣ�����ʧ��Ч��
	 * @param imgsrc
	 * @param imgdist
	 * @param x
	 */
	public static void reduceImg(String imgsrc, String imgdist, int x) {
		try {
			File srcfile = new File(imgsrc);
			if (!srcfile.exists()) {
				return;
			}
			Image src = javax.imageio.ImageIO.read(srcfile);

			double owidth = (double) src.getWidth(null); // �õ�Դͼ��
			double oheight = (double) src.getHeight(null); // �õ�Դͼ��
			if (x >= owidth && x >= oheight)
				return;
			int iWidth = 0;
			int iHeight = 0;
			if (owidth > oheight) {
				// ����ȱ�׼����
				iWidth = x;
				iHeight = (int) (oheight * (x / owidth));
			} else {
				// ���߶ȱ�׼����
				iWidth = (int) (owidth * (x / oheight));
				iHeight = x;
			}

			BufferedImage tag = new BufferedImage((int) iWidth, (int) iHeight,
					BufferedImage.TYPE_INT_RGB);

			tag.getGraphics().drawImage(
					src.getScaledInstance(iWidth, iHeight, Image.SCALE_SMOOTH),
					0, 0, null);
			// / tag.getGraphics().drawImage(src.getScaledInstance(widthdist,
			// heightdist, Image.SCALE_AREA_AVERAGING), 0, 0, null);

			FileOutputStream out = new FileOutputStream(imgdist);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag);
			out.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * @param src
	 *            Ҫ���ŵ�ͼƬ��ַ
	 * @param tar
	 *            ͼƬ�����Ŀ���ַ
	 * @param height
	 *            ָ�����ź��ͼƬ���
	 * @throws Exception
	 */
	public static void imageZoom(String src, String tar, int x)
			throws NullSourceException {
		System.out.println("ԭ�ļ������ַ:" + src);
		System.out.println("Ŀ���ļ������ַ:" + tar);
		if (src == null)
			throw new NullSourceException("Ҫת����ͼƬԴ�ļ�Ϊ�գ�");
		// if(!tar.toLowerCase().endsWith("jpg"))tar+=".jpg";
		try {
			String tmp = tar;
			toJPG(src, tmp, 100); // ת��������100��jpgͼƬ
			File f = new File(src);
			Image img = javax.imageio.ImageIO.read(f);
			double owidth = (double) img.getWidth(null); // �õ�Դͼ��
			double oheight = (double) img.getHeight(null); // �õ�Դͼ��
			if (x > owidth && x > oheight)
				return;
			int iWidth = 0;
			int iHeight = 0;
			if (owidth > oheight) {
				// ����ȱ�׼����
				iWidth = x;
				iHeight = (int) (oheight * (x / owidth));
			} else {
				// ���߶ȱ�׼����
				iWidth = (int) (owidth * (x / oheight));
				iHeight = x;
			}
			BufferedImage tag = new BufferedImage(iWidth, iHeight,
					BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(img, 0, 0, iWidth, iHeight, null);
			if (!tmp.equals(tar))
				f.deleteOnExit();
			Jimi.putImage(tag, tar);
		} catch (Exception e) {
			System.out.println("ͼƬת�������쳣��");
			e.printStackTrace();
		}
	}

	public static void toJPG(String src, String tar, int qlt) throws Exception {
		try {
			JPGOptions options = new JPGOptions();
			options.setQuality(qlt);
			ImageProducer image = Jimi.getImageProducer(src);
			JimiWriter writer = Jimi.createJimiWriter(tar);
			writer.setSource(image);
			writer.setOptions(options);
			writer.putImage(tar);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getSmallPictureUrl(String bigPictureUrl) {
		if (bigPictureUrl != null && !bigPictureUrl.equals("")) {
			int dotPos = bigPictureUrl.lastIndexOf(".");
			String pre = bigPictureUrl.substring(0, dotPos);
			String next = bigPictureUrl.substring(dotPos);
			pre += ".small";
			return pre + next;
		}
		return null;
	}

	// public static void main(String[] args)
	// {
	//
	// imageZoom("D:\\test\\large\\2.jpg", "D:\\test\\small\\2.jpg", 590); //
	// ��������С���߿��������100

	// String bigPic = "abc\\upImages\\1\\big.jpg";
// String smallPic = getSmallPictureUrl(bigPic);
	// System.out.println(smallPic);
	// }

}
