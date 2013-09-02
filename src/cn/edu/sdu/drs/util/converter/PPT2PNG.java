package cn.edu.sdu.drs.util.converter;

import org.apache.poi.hslf.usermodel.*;
import org.apache.poi.hslf.model.*;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.Rectangle2D;

public final class PPT2PNG {

	public static int convert(final String pptFilePath, final String pngFileDir)
			throws IOException {
		
		String pptName=new File(pptFilePath).getName();
		
		int slidenum = -1;
		float scale = 1;
		
		FileInputStream is = new FileInputStream(pptFilePath);
		SlideShow ppt = new SlideShow(is);
		is.close();
		
		Dimension pgsize = ppt.getPageSize();
		int width = (int) (pgsize.width * scale);
		int height = (int) (pgsize.height * scale);

		Slide[] slide = ppt.getSlides();
		for (int i = 0; i < slide.length; i++) {
			if (slidenum != -1 && slidenum != (i + 1))
				continue;

			String title = slide[i].getTitle();
			System.out.println("Rendering slide " + slide[i].getSlideNumber()
					+ (title == null ? "" : ": " + title));

			BufferedImage img = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = img.createGraphics();
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
					RenderingHints.VALUE_FRACTIONALMETRICS_ON);

			graphics.setPaint(Color.white);
			graphics.fill(new Rectangle2D.Float(0, 0, width, height));

			graphics.scale((double) width / pgsize.width, (double) height
					/ pgsize.height);

			slide[i].draw(graphics);

			String fname = pngFileDir +pptName+"-"+(i + 1) + ".png";
			FileOutputStream out = new FileOutputStream(fname);
			ImageIO.write(img, "png", out);
			out.close();
		}
		
		return slide.length;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(PPT2PNG.convert("E:/temp/ppt.ppt", "E:/temp/ppt/"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}