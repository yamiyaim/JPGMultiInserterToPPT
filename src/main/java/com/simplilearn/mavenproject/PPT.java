package com.simplilearn.mavenproject;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.poi.sl.usermodel.PictureData.PictureType;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;

public class PPT {
	// take directory with images and set images to PPT which is named as directory
	// name
	public static void writePPT(int width, int height, String dirPath) throws FileNotFoundException, IOException {
		File dir = new File(dirPath);
		File[] jpgfiles;

		// open sample.pptx with PPT
		XMLSlideShow ppt = new XMLSlideShow(new FileInputStream("src//sample.pptx"));

		// set page size
		Dimension size = new Dimension(width, height);
		ppt.setPageSize(size);

		// filtering jpeg and jpg files
		jpgfiles = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith("jpg") || name.endsWith("jpeg");
			}
		});

		// setting images to PPT
		for (File file : jpgfiles) {
			setImageSlidetoPPT(ppt, file.toString());
		}

		// write copy.pptx
		FileOutputStream out = new FileOutputStream(dirPath + "\\" + dir.getName() + ".pptx");
		ppt.write(out);
		out.close();
	}

	// set image to PPT
	public static void setImageSlidetoPPT(XMLSlideShow ppt, String imgPath) throws FileNotFoundException, IOException {

		int pptHeight = ppt.getPageSize().height;

		// create new slide
		XSLFSlide slide = ppt.createSlide();

		// put image to slide
		byte[] jpgData = IOUtils.toByteArray(new FileInputStream(imgPath));
		XSLFPictureData pd = ppt.addPicture(jpgData, PictureType.JPEG);
		XSLFPictureShape picture = slide.createPicture(pd);

		// set anchor in slide
		BufferedImage bimg = ImageIO.read(new File(imgPath));
		double imgWidth = (double) bimg.getWidth() / bimg.getHeight() * pptHeight;
		picture.setAnchor(new Rectangle(0, 0, (int) imgWidth, pptHeight));

	}

}
