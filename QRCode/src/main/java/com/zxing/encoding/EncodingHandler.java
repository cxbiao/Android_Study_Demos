package com.zxing.encoding;

import java.util.Hashtable;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * @author Ryan Tang
 * 
 */
public final class EncodingHandler {
	private static final int BLACK = 0xff000000;

	public static Bitmap createQRCode(String str, int widthAndHeight) throws WriterException {
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		int[] pixels = new int[width * height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = BLACK;
				}
			}
		}
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	/*
	 * 用字符串生成二维码
	 * 
	 * @param str
	 * 
	 * @author zhouzhe@lenovo-cw.com
	 * 
	 * @return
	 * 
	 * @throws WriterException
	 */
	public static Bitmap Create2DCode(String str, int picWidth, int picHeight) throws WriterException {
		// 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
		Hashtable hints = new Hashtable();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, picWidth, picHeight, hints);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		// 二维矩阵转为一维像素数组,也就是一直横着排了
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = 0xff000000;
				} else {
					pixels[y * width + x] = 0xffffffff;
				}

			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		// 通过像素数组生成bitmap,具体参考api
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	// 解析QR图片
	public static String scanningImage(Bitmap bitmap) {

		// HashMap<EncodeHintType, String> hints = new
		// HashMap<EncodeHintType, String>();
		// hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

		Hashtable hints = new Hashtable<DecodeHintType, String>();
		hints.put(DecodeHintType.CHARACTER_SET, "utf-8");

		RGBLuminanceSource source = new RGBLuminanceSource(bitmap);
		BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
		QRCodeReader reader = new QRCodeReader();
		Result result;
		try {
			result = reader.decode(bitmap1, hints);
		//	System.out.println("res：》》》》》》》：" + result.getText());
			return result.getText().toString();
		} catch (NotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (ChecksumException e) {
			e.printStackTrace();
			return null;
		} catch (FormatException e) {
			e.printStackTrace();
			return null;
		}

	}

	private static final int PADDING_SIZE_MIN = 0; // 最小留白长度, 单位: px,用于设置是否留白边
	public static Bitmap createQRCodeWithoutBorder(String str, int widthAndHeight) throws WriterException {
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight, hints);

		int width = matrix.getWidth();
		int height = matrix.getHeight();
		int[] pixels = new int[width * height];

		boolean isFirstBlackPoint = false;
		int startX = 0;
		int startY = 0;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					if (isFirstBlackPoint == false) {
						isFirstBlackPoint = true;
						startX = x;
						startY = y;

					}
					pixels[y * width + x] = BLACK;
				}
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

		// 剪切中间的二维码区域，减少padding区域
		if (startX <= PADDING_SIZE_MIN)
			return bitmap;

		int x1 = startX - PADDING_SIZE_MIN;
		int y1 = startY - PADDING_SIZE_MIN;
		if (x1 < 0 || y1 < 0)
			return bitmap;

		int w1 = width - x1 * 2;
		int h1 = height - y1 * 2;

		Bitmap bitmapQR = Bitmap.createBitmap(bitmap, x1, y1, w1, h1);

		return bitmapQR;
	}
}
