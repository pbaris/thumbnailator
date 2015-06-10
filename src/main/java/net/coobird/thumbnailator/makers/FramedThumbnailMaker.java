package net.coobird.thumbnailator.makers;

import net.coobird.thumbnailator.builders.BufferedImageBuilder;
import net.coobird.thumbnailator.filters.Watermark;
import net.coobird.thumbnailator.geometry.Positions;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created on 10/6/2015.
 *
 * @author Panos Bariamis
 */
public final class FramedThumbnailMaker extends FixedSizeThumbnailMaker {

    private static final String PARAM_USE_FRAME = "useFrame";
    private static final String PARAM_FRAME_COLOR = "frameColor";

    private Color frameColor;

    public FramedThumbnailMaker(int width, int height) {
        super(width, height);
        ready.set(PARAM_USE_FRAME);
        ready.unset(PARAM_FRAME_COLOR);
    }

    public FramedThumbnailMaker frameColor(Color color) {
        if (ready.isSet(PARAM_FRAME_COLOR)) {
            throw new IllegalStateException("The frame color has already been set.");
        }

        if (color == null) {
            throw new IllegalArgumentException("Color it's not an instance");
        }

        this.frameColor = color;

        ready.set(PARAM_FRAME_COLOR);
        return this;
    }

    @Override
    public FramedThumbnailMaker size(int width, int height) {
        return (FramedThumbnailMaker)super.size(width, height);
    }

    @Override
    public FramedThumbnailMaker keepAspectRatio(boolean keep) {
        return (FramedThumbnailMaker)super.keepAspectRatio(keep);
    }

    @Override
    public FramedThumbnailMaker fitWithinDimensions(boolean fit) {
        return (FramedThumbnailMaker)super.fitWithinDimensions(fit);
    }

    @Override
    public BufferedImage make(BufferedImage img) {
        int dim = Math.max(img.getWidth(), img.getHeight());

        BufferedImage framedImage = new BufferedImageBuilder(dim, dim, img.getType()).build();

        //TODO parametrize color
        Graphics2D g = framedImage.createGraphics();
        g.setColor(frameColor);
        g.fillRect(0, 0, dim, dim);

        Watermark wm = new Watermark(Positions.CENTER, img, 1.0f);

        framedImage = wm.apply(framedImage);

        return super.make(framedImage);
    }
}