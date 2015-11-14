package com.bng.core.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.bng.core.util.LogValues;
import com.bng.core.util.Logger;
import com.mxgraph.canvas.mxGraphicsCanvas2D;
//import com.mxgraph.canvas.mxICanvas2D;
import com.mxgraph.reader.mxSaxOutputHandler;
import com.mxgraph.util.mxUtils;

@Controller
public class canvasController {

	/**
	 * 
	 */
	private transient SAXParserFactory parserFactory = SAXParserFactory
			.newInstance();

	/**
	 * Cache for all images.
	 */
	private transient Hashtable<String, Image> imageCache = new Hashtable<String, Image>();
	
	@RequestMapping("/export.htm")
	public String export(HttpServletRequest request,HttpServletResponse response ,@RequestParam("filename") String fname,@RequestParam("format") String format,@RequestParam("w") String widths,@RequestParam("h") String heights,@RequestParam("bg") String background,@RequestParam("plain") String plain,Principal principal) throws IOException, SAXException, ParserConfigurationException
	{
		
		
		String xml = "<root>"+URLDecoder.decode(plain, "UTF-8")+"</root>";
		int width = Integer.parseInt(widths);
		int height = Integer.parseInt(heights);
		Logger.sysLog(LogValues.info, this.getClass().getName(),"filename::"+fname+"::format::"+format+"::width::"+widths+"::height::"+heights+"::bg::"+background);
		Logger.sysLog(LogValues.info, this.getClass().getName(),"filename::"+xml);
		Color bg = (background != null) ? mxUtils.parseColor(background) : Color.WHITE;
		if (width > 0 && width <= Constants.MAX_WIDTH && height> 0 && height <= Constants.MAX_HEIGHT && format != null && xml != null && xml.length() > 0)
		{
			if (fname != null && fname.toLowerCase().endsWith(".xml"))
			{
				fname = fname.substring(0, fname.length() - 4) + format;
			}

			String url = request.getRequestURL().toString();

			// Writes response
			
			
				writeImage(url, format, fname, width, height, bg, xml, response);
			

			response.setStatus(HttpServletResponse.SC_OK);
		}
		else
		{
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	
		return "save";
	}
	
	
	protected void writeImage(String url, String format, String fname, int w, int h,
			Color bg, String xml, HttpServletResponse response)
			throws IOException, SAXException, ParserConfigurationException
	{
		BufferedImage image = mxUtils.createBufferedImage(w, h, bg);

		if (image != null)
		{
			Graphics2D g2 = image.createGraphics();
			mxUtils.setAntiAlias(g2, true, true);
			renderXml(xml, createCanvas(url, g2));

			if (fname != null)
			{
			//	response.setContentType("application/x-unknown");
			//	response.setHeader("Content-Disposition",
				//		"attachment; filename=\"" + fname + "\"");
			}
			else if (format != null)
			{
			//	response.setContentType("image/" + format.toLowerCase());
			}

			File f=new File("/home/service_image/bng.png");
			File p=new File(f.getParent());
			if(!p.exists())
			{
				p.mkdirs();
			}				
			//ImageIO.write(image, format, response.getOutputStream());
			//ImageIO.write(image, format, new File("/home/vikas/bng.png"));
			ImageIO.write(image, format, f);
		}
	}
	protected mxGraphicsCanvas2D createCanvas(String url, Graphics2D g2)
	{
		// Caches custom images for the time of the request
		final Hashtable<String, Image> shortCache = new Hashtable<String, Image>();
		final String domain = url.substring(0, url.lastIndexOf("/"));

		mxGraphicsCanvas2D g2c = new mxGraphicsCanvas2D(g2)
		{
			public Image loadImage(String src)
			{
				// Uses local image cache by default
				Hashtable<String, Image> cache = shortCache;

				// Uses global image cache for local images
				if (src.startsWith(domain))
				{
					cache = imageCache;
				}

				Image image = cache.get(src);

				if (image == null)
				{
					image = super.loadImage(src);

					if (image != null)
					{
						cache.put(src, image);
					}
					else
					{
						cache.put(src, Constants.EMPTY_IMAGE);
					}
				}
				else if (image == Constants.EMPTY_IMAGE)
				{
					image = null;
				}

				return image;
			}
		};

		g2c.setAutoAntiAlias(true);

		return g2c;
	}
	protected void renderXml(String xml, mxGraphicsCanvas2D canvas)
			throws SAXException, ParserConfigurationException, IOException
	{
		XMLReader reader = parserFactory.newSAXParser().getXMLReader();
		reader.setContentHandler(new mxSaxOutputHandler(canvas));
		reader.parse(new InputSource(new StringReader(xml)));
	}
}
