package net.coobird.thumbnailator.tasks;

import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.resizers.Resizers;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

public class FileThumbnailTaskTest
{

	@Test(expected=NullPointerException.class)
	public void nullParameter() throws IOException
	{
		// given
		File inputFile = new File("src/test/resources/Thumbnailator/grid.jpg");
		File outputFile = File.createTempFile("thumbnailator-testing-", ".png");
		outputFile.deleteOnExit();

		try
		{
			// when
			new FileThumbnailTask(null, inputFile, outputFile);
			fail();
		}
		catch (NullPointerException e)
		{
			// then
			assertEquals("The parameter is null.", e.getMessage());
			throw e;
		}
	}

	@Test
	public void testRead_CorrectUsage() throws IOException
	{
		ThumbnailParameter param = new ThumbnailParameter(
				new Dimension(50, 50),
				null,
				true,
				"jpg",
				ThumbnailParameter.DEFAULT_FORMAT_TYPE,
				ThumbnailParameter.DEFAULT_QUALITY,
				BufferedImage.TYPE_INT_ARGB,
				null,
				Resizers.PROGRESSIVE,
				true,
				true,
                false,
                Color.WHITE
		);

		File inputFile = new File("src/test/resources/Thumbnailator/grid.jpg");
		File outputFile = File.createTempFile("thumbnailator-testing-", ".png");
		outputFile.deleteOnExit();

		FileThumbnailTask task =
			new FileThumbnailTask(param, inputFile, outputFile);

		task.read();
	}

	@Ignore
	public void testWrite()
	{
		fail("Not yet implemented");
	}

	@Ignore
	public void testFileThumbnailTask()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetParam()
	{
		ThumbnailParameter param = new ThumbnailParameter(
				new Dimension(50, 50),
				null,
				true,
				"jpg",
				ThumbnailParameter.DEFAULT_FORMAT_TYPE,
				ThumbnailParameter.DEFAULT_QUALITY,
				BufferedImage.TYPE_INT_ARGB,
				null,
				Resizers.PROGRESSIVE,
				true,
				true,
                false,
                Color.WHITE
		);

		InputStream is = mock(InputStream.class);
		OutputStream os = mock(OutputStream.class);

		StreamThumbnailTask task = new StreamThumbnailTask(param, is, os);

		assertEquals(param, task.getParam());

		verifyZeroInteractions(is);
		verifyZeroInteractions(os);
	}
}
