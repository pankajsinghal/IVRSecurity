package com.bng.core.controller;

import java.awt.image.BufferedImage;

public class Constants
{
	/**
	 * Contains an empty image.
	 */
	public static BufferedImage EMPTY_IMAGE;

	/**
	 * Initializes the empty image.
	 */
	static
	{
		try
		{
			EMPTY_IMAGE = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		}
		catch (Exception e)
		{
			// ignore
		}
	}

	/**
	 * Maximum size (in bytes) for request payloads. Default is 209715200 (200MB).
	 */
	public static final int MAX_REQUEST_SIZE = 209715200;

	/**
	 * Maximum width for ex�prts. Default is 5000px.
	 */
	public static final int MAX_WIDTH = 10000;

	/**
	 * Maximum height for exports. Default is 5000px.
	 */
	public static final int MAX_HEIGHT = 10000;

}
