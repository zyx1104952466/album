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
 * 图片缩放类
 * 
 * @author FuPeng
 * 
 * Jul 15, 2009
 */
public class ImageZoom {
	
	/**
	 * 缩放模糊锯齿，降低失真效果
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

			double owidth = (double) src.getWidth(null); // 得到源图宽
			double oheight = (double) src.getHeight(null); // 得到源图高
			if (x >= owidth && x >= oheight)
				return;
			int iWidth = 0;
			int iHeight = 0;
			if (owidth > oheight) {
				// 按宽度标准缩放
				iWidth = x;
				iHeight = (int) (oheight * (x / owidth));
			} else {
				// 按高度标准缩放
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
	 *            要缩放的图片地址
	 * @param tar
	 *            图片保存的目标地址
	 * @param height
	 *            指定缩放后的图片宽度
	 * @throws Exception
	 */
	public static void imageZoom(String src, String tar, int x)
			throws NullSourceException {
		System.out.println("原文件物理地址:" + src);
		System.out.println("目的文件物理地址:" + tar);
		if (src == null)
			throw new NullSourceException("要转换的图片源文件为空！");
		// if(!tar.toLowerCase().endsWith("jpg"))tar+=".jpg";
		try {
			String tmp = tar;
			toJPG(src, tmp, 100); // 转换成质量100的jpg图片
			File f = new File(src);
			Image img = javax.imageio.ImageIO.read(f);
			double owidth = (double) img.getWidth(null); // 得到源图宽
			double oheight = (double) img.getHeight(null); // 得到源图高
			if (x > owidth && x > oheight)
				return;
			int iWidth = 0;
			int iHeight = 0;
			if (owidth > oheight) {
				// 按宽度标准缩放
				iWidth = x;
				iHeight = (int) (oheight * (x / owidth));
			} else {
				// 按高度标准缩放
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
			System.out.println("图片转换产生异常！");
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
	// 按比例缩小，高宽均不超过100

	// String bigPic = "abc\\upImages\\1\\big.jpg";
// String smallPic = getSmallPictureUrl(bigPic);
	// System.out.println(smallPic);
	// }

}
