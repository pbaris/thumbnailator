package net.coobird.thumbnailator.tasks;

import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.builders.BufferedImageBuilder;
import net.coobird.thumbnailator.resizers.Resizers;
import net.coobird.thumbnailator.test.BufferedImageComparer;
import org.junit.Ignore;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class StreamThumbnailTaskTest
{
	@Test(expected=NullPointerException.class)
	public void nullParameter() throws IOException
	{
		// given
		InputStream is = mock(InputStream.class);
		OutputStream os = mock(OutputStream.class);

		try
		{
			// when
			new StreamThumbnailTask(null, is, os);
			fail();
		}
		catch (NullPointerException e)
		{
			// then
			assertEquals("The parameter is null.", e.getMessage());
			verifyZeroInteractions(is);
			verifyZeroInteractions(os);
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
				"png",
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

		InputStream spyIs = spy(new FileInputStream(inputFile));
		OutputStream spyOs = spy(new FileOutputStream(outputFile));

		StreamThumbnailTask task = new StreamThumbnailTask(param, spyIs, spyOs);
		BufferedImage img = task.read();

		assertTrue(BufferedImageComparer.isSame(img, ImageIO.read(inputFile)));

		verify(spyIs, never()).close();
		verifyZeroInteractions(spyOs);
	}

	@Test
	public void testWrite_CorrectUsage() throws IOException
	{
		ThumbnailParameter param = new ThumbnailParameter(
				new Dimension(50, 50),
				null,
				true,
				"png",
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

		InputStream spyIs = spy(new FileInputStream(inputFile));
		OutputStream spyOs = spy(new FileOutputStream(outputFile));

		StreamThumbnailTask task = new StreamThumbnailTask(param, spyIs, spyOs);
		BufferedImage img = new BufferedImageBuilder(50, 50).build();

		task.write(img);

		verifyZeroInteractions(spyIs);
		verify(spyOs, never()).close();

		BufferedImage outputImage = ImageIO.read(outputFile);
		assertTrue(BufferedImageComparer.isRGBSimilar(img, outputImage));
	}

	@Ignore
	public void testStreamThumbnailTask()
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
				"png",
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