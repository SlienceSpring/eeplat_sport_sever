package com.exedosoft.plat.weixin.qrcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.storage.oss.OSSUpload;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.ImageUtil;
import com.exedosoft.plat.util.sequence.DOMAXIDBuilder;

public class QRCodeImage {

	public QRCodeImage() {
		// TODO Auto-generated constructor stub
	}

	public BOInstance createCompanyQRCode(String companyUid) {

		DOService findCompany = DOService.getService("llp_company_browse");
		BOInstance<?> c = findCompany.getInstance(companyUid);
		// /////只生成一遍
		String wx_logo_url = c.getValue("wx_logo_url");
		String shortname = c.getValue("shortname");
		if (wx_logo_url != null) {

			return c;
		}

		int aID = DOMAXIDBuilder.getInstance().getID("QRCODE");
		String wx_scene_id = "A_" + aID;
		String wx_ticket = QRCode.createPermanentQRCode(wx_scene_id);
		String wx_qrcode_url = QRCode.getQRCodeURL(wx_ticket);

		String srcQrCodePath = downImage(wx_qrcode_url, wx_scene_id, true);

		String toEnd2 = mkOneImage(shortname, "a02.jpg", wx_scene_id,
				srcQrCodePath);
		String toEnd3 = mkOneImage(shortname, "a03.jpg", wx_scene_id,
				srcQrCodePath);
		String toEnd4 = mkOneImage(shortname, "a04.jpg", wx_scene_id,
				srcQrCodePath);

		String toEnd = DOGlobals.getValue("weixin.apiclient.cert")
				+ File.separator + "temp" + File.separator + wx_scene_id
				+ "_full.jpg";
		String[] pics = new String[] { toEnd2, toEnd3, toEnd4 };
		ImageUtil imageObj = new ImageUtil();
		imageObj.joinImageListVertical(pics, "jpg", toEnd);

		try {

			OSSUpload.upload(wx_scene_id + ".jpg", toEnd);

			Map paras = new HashMap();
			paras.put("wx_scene_str", wx_scene_id);
			paras.put("wx_ticket", wx_ticket);
			paras.put("wx_qrcode_url", wx_qrcode_url);
			paras.put("companyuid", companyUid);
			paras.put("wx_logo_url", getAliYunUrl(wx_scene_id + ".jpg"));

			DOService service = DOService.getService("c_weixin");
			try {
				c = service.invokeUpdate(paras);
			} catch (ExedoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// if (srcLogoPath != null) {
			// imageObj.mergeBothImage(toEnd, srcLogoPath, 1479, 80, toEnd); //
			// 按指定坐标合并图片,logo
			// }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return c;
	}

	private String mkOneImage(String companyName, String baseImgName,
			String wx_scene_id, String srcQrCodePath) {

		String basePath = DOGlobals.getValue("weixin.apiclient.cert")
				+ File.separator + baseImgName;

		// String srcLogoPath = null;
		// if (logoimg != null) {
		// String aliYunLogoUrl = getAliYunUrl(logoimg);
		// srcLogoPath = downImage(aliYunLogoUrl, wx_scene_id + "_logo", false);
		// }

		String toEnd = DOGlobals.getValue("weixin.apiclient.cert")
				+ File.separator + "temp" + File.separator + wx_scene_id + "_"
				+ baseImgName + ".jpg";

		ImageUtil imageObj = new ImageUtil();

		try {
			imageObj.mergeBothImage(basePath, srcQrCodePath, 1056, 636, toEnd); // 按指定坐标合并图片,二维码

			int shortNameSize = companyName.length(); 
			
			imageObj.alphaWords2Image(toEnd, 0f,
					DOGlobals.getValue("awt.font"), Font.BOLD, 100, Color.white,
					companyName, (2480-170-100*shortNameSize), 138, "jpg", toEnd);// 公司名称
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return toEnd;
	}

	private String getAliYunUrl(String aUrl) {

		StringBuffer buffer = new StringBuffer();
		try {
			buffer.append(DOGlobals.getValue("oss.bucket.path")).append(
					URLEncoder.encode(aUrl, "utf-8"));
		} catch (UnsupportedEncodingException e) {

		}
		return buffer.toString();

	}

	private static String downImage(String urlImage, String wx_scene_id,
			boolean isQRcode) {

		String tempPath = DOGlobals.getValue("weixin.apiclient.cert")
				+ File.separator + "temp";
		File fileTemp = new File(tempPath);
		if (!fileTemp.exists()) {
			fileTemp.mkdir();
		}
		tempPath = tempPath + File.separator + wx_scene_id + ".jpg";

		try {
			// 实例化url
			URL url = new URL(urlImage);
			// 载入图片到输入流
			java.io.BufferedInputStream bis = new BufferedInputStream(
					url.openStream());
			// 实例化存储字节数组
			byte[] bytes = new byte[100];
			// 设置写入路径以及图片名称
			OutputStream bos = new FileOutputStream(new File(tempPath));
			int len;
			while ((len = bis.read(bytes)) > 0) {
				bos.write(bytes, 0, len);
			}
			bis.close();
			bos.flush();
			bos.close();
		} catch (Exception e) {
		}

		ImageUtil imageObj = new ImageUtil();
		try {
			if (isQRcode) {
				imageObj.resizeImage(tempPath, tempPath, 340, 340);
			} else {
				imageObj.resizeImage(tempPath, tempPath, 850, 180);// 按指定的长宽重置图形大小
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 按指定的长宽重置图形大小
		return tempPath;
	}

	public static void main(String[] args) {

		DOGlobals g = DOGlobals.getInstance();
		QRCodeImage qi = new QRCodeImage();
		BOInstance c = qi
				.createCompanyQRCode("8af4e49d4ea1e271014eaae952230051");

		System.out.println("BOInstance :: " + c);
	}

	// public static void main(String[] args) throws IOException {
	// ImageUtil imageObj = new ImageUtil();
	//
	// String basePath = "D:/a02.jpg";
	// String srcQrCodePath = downImage(
	// "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQGR8ToAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL1MwVi1oZUxtRVV0bzBMVlFlMnZEAAIE09q6VQMEgDoJAA==",
	// "aabb", true);
	//
	// String toEnd = "D:/end2.jpg";
	//
	// imageObj.mergeBothImage(basePath, srcQrCodePath, 1056, 636, toEnd); //
	// 按指定坐标合并图片
	// imageObj.alphaWords2Image(toEnd, 1f, "宋体", Font.BOLD, 65, Color.BLACK,
	// "北京云荷素科技有限公司", 1479, 110, "jpg", toEnd);
	//
	// }

	// public static void main(String[] args) throws Exception {
	// int width = 100;
	// int height = 100;
	// System.err.println(System.getProperty("sun.jnu.encoding"));
	// String s1 = "时段";
	// // String s2 = new
	// // String("你好".getBytes(System.getProperty("sun.jnu.encoding")),
	// // "UTF-8");
	// // String s3 = new String("你好".getBytes("GBK"),
	// // System.getProperty("sun.jnu.encoding"));
	// // String s4 = new String("你好".getBytes(),
	// // System.getProperty("sun.jnu.encoding"));
	//
	// File file = new File("D:/a05.jpg");
	//
	// Font font = new Font("Serif", Font.BOLD, 10);
	// BufferedImage bi = new BufferedImage(width, height,
	// BufferedImage.TYPE_INT_RGB);
	// Graphics2D g2 = (Graphics2D) bi.getGraphics();
	// g2.setBackground(Color.WHITE);
	// g2.clearRect(0, 0, width, height);
	// g2.setPaint(Color.RED);
	//
	// // FontRenderContext context = g2.getFontRenderContext();
	// // Rectangle2D bounds = font.getStringBounds(s1 , context);
	// // double x = (width - bounds.getWidth()) / 2;
	// // double y = (height - bounds.getHeight()) / 2;
	// // double ascent = -bounds.getY();
	// // double baseY = y + ascent;
	//
	// g2.drawString(s1, (int) 50, (int) 50);
	//
	// ImageIO.write(bi, "jpg", file);
	// }

}
