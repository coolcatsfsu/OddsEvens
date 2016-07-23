package com.aec12e.opencvtut;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by antoniomojics on 7/21/2016.
 */
public class IdentifyColor {
    private static double outlineMINarea = 0.1;
    private Scalar colorRange = new Scalar(25, 50, 50, 0), bottomRange = new Scalar(0), topRange = new Scalar(0);
    private Mat Spectrum = new Mat();
    private List<MatOfPoint> Outlines = new ArrayList<MatOfPoint>();

    Mat pyramid = new Mat(), hsv = new Mat(), mask = new Mat(), maskDilated = new Mat(), hierarchy = new Mat();

    public void ColorRangeON(Scalar color_range) {
        colorRange = color_range;
    }

    public void hsvON(Scalar hsv_) {
        double min, max;
        min = (hsv_.val[0] >= colorRange.val[0]) ? hsv_.val[0] - colorRange.val[0] : 0;
        max = (hsv_.val[0] + colorRange.val[0] <= 225) ? hsv_.val[0] + colorRange.val[0] : 225;

        bottomRange.val[0] = min;
        topRange.val[0] = max;
        bottomRange.val[1] = hsv_.val[1] - colorRange.val[1];
        topRange.val[1] = hsv_.val[1] + colorRange.val[1];
        bottomRange.val[2] = hsv_.val[2] - colorRange.val[2];
        topRange.val[2] = hsv_.val[2] + colorRange.val[2];
        bottomRange.val[3] = 0;
        topRange.val[3] = 225;

        Mat hsv_spectrum;
        hsv_spectrum = new Mat(1, (int)(max-min), CvType.CV_8UC3);
        int r;
        r = 0;
        byte[] temp = {
                (byte)(min+r),
                (byte)225,
                (byte)225
        };
        while (r < max-min) {
            hsv_spectrum.put(0, r, temp);
            r++;
        }

        Imgproc.cvtColor(hsv_spectrum, Spectrum, Imgproc.COLOR_HSV2BGR_FULL, 4);
    }

    public Mat SpectrumIS() {
        return Spectrum;
    }

    public void OutlineMINareON(double outlineMINarea_) {
        outlineMINarea = outlineMINarea_;
    }

    public void Analyze(Mat rgba_picture) {
        Imgproc.pyrDown(rgba_picture, pyramid);
        Imgproc.pyrDown(pyramid,pyramid);
        Imgproc.cvtColor(pyramid, hsv, Imgproc.COLOR_RGB2HSV_FULL);

        Core.inRange(hsv, bottomRange, topRange, mask);
        Imgproc.dilate(mask, maskDilated, new Mat());

        List<MatOfPoint> outlines_ = new ArrayList<MatOfPoint>();

        Imgproc.findContours(maskDilated, outlines_, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        double areaMAX;
        areaMAX = 0;
        Iterator<MatOfPoint> all = outlines_.iterator();
        if (all.hasNext()) {
            do {
                MatOfPoint wrap;
                wrap = all.next();
                double area;
                area = Imgproc.contourArea(wrap);

                if (area > areaMAX) {
                    areaMAX = area;
                }
            } while (all.hasNext());
        }

        Outlines.clear();
        all = outlines_.iterator();
        if (all.hasNext()) {
            do {
                MatOfPoint outline;
                outline = all.next();

                if (Imgproc.contourArea(outline) > outlineMINarea*areaMAX) {
                    Core.multiply(outline, new Scalar(4, 4), outline);
                    Outlines.add(outline);
                }
            } while (all.hasNext());
        }
    }

    public List<MatOfPoint> OutlinesARE() {
        return Outlines;
    }
}
