package com.mhm.xq.utils

import android.graphics.Bitmap
import android.graphics.Matrix
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import java.util.*


class QRCodeUtil {
    companion object {
        fun createImage(text: String, w: Int, h: Int, logo: Bitmap): Bitmap {
            var scaleLogo: Bitmap? = getScaleLogo(logo, w, h)
            var offsetX: Int = (w - scaleLogo!!.width) / 2
            var offsetY: Int = (h - scaleLogo!!.height) / 2
            val hints = Hashtable<EncodeHintType, Any>()
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8")
            hints.put(EncodeHintType.MARGIN, 0)
            val bitMatrix = QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, w, h, hints)
            val pixels = IntArray(w * h)
            for (y in 0 until h) {
                for (x in 0 until w) {
                    if (x >= offsetX && x < offsetX + scaleLogo.width && y >= offsetY && y < offsetY + scaleLogo.height) {
                        var pixel = scaleLogo.getPixel(x - offsetX, y - offsetY)
                        if (pixel == 0) {
                            if (bitMatrix.get(x, y)) {
                                pixel = -0x1000000
                            } else {
                                pixel = -0x1
                            }
                        }
                        pixels[y * w + x] = pixel
                    } else {
                        if (bitMatrix.get(x, y)) {
                            pixels[y * w + x] = -0x1000000
                        } else {
                            pixels[y * w + x] = -0x1
                        }
                    }
                }
            }
            val bitmap = Bitmap.createBitmap(w, h,
                    Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h)
            return bitmap
        }

        fun getScaleLogo(logo: Bitmap, w: Int, h: Int): Bitmap? {
            if (logo == null) {
                return null
            }
            var matrix: Matrix = Matrix()
            var scaleFactor: Float = Math.min(w * 1.0f / 5 / logo.width, h * 1.0f / 5 / logo.height)
            matrix.postScale(scaleFactor, scaleFactor)
            var result: Bitmap = Bitmap.createBitmap(logo, 0, 0, logo.width, logo.height, matrix, true)
            return result
        }
    }
}