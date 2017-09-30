package com.eric.self.photolibrary.images;

/**
 * 蜂鸟网的图片素材
 * <p>
 * http://www.fengniao.com/
 *
 * @author clifford
 */
public class CommonImageSource implements ImageSource {

    private String mOriginUrl;
    private String mThumbUrl;
    private int mThumbWidth;
    private int mThumbHeight;

    public CommonImageSource(String originUrl, String thumbUrl, int thumbWidth, int thumbHeight) {
        mOriginUrl = originUrl;
        mThumbUrl = thumbUrl;
        mThumbWidth = thumbWidth;
        mThumbHeight = thumbHeight;
    }

    @Override
    public ImageObject getThumb() {
        ImageObject imageObject = new ImageObject();
        imageObject.url = mThumbUrl;
        imageObject.width = mThumbWidth;
        imageObject.height = mThumbHeight;
        return imageObject;
    }

    @Override
    public ImageObject getOrigin() {
        ImageObject imageObject = new ImageObject();
        imageObject.url = mOriginUrl;
        imageObject.width = 0;
        imageObject.height = 0;
        return imageObject;
    }
}