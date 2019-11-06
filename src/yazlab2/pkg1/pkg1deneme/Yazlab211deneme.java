/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yazlab2.pkg1.pkg1deneme;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//Bunların çalışması için kütüphanenin dosya yolunu masaüstüne verdiğimden,kütüphanelerin masaüstünde olması gerek.
import java.util.ArrayList;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.*;
import net.sourceforge.tess4j.*;

/**
 *
 * @author asus1
 */
public class Yazlab211deneme extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("OCR Project");
        stage.setScene(scene);
        stage.show();
    }

    public static void filter() {
        int alpha = 5, beta = 40;
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        try {
            // imread ile filtrelenecek resim belirleniyor
            Mat source = Imgcodecs.imread("C:\\Users\\asus1\\Documents\\NetBeansProjects\\yazlab2.1.1deneme\\src\\yazlab2\\pkg1\\pkg1deneme\\Fisler\\fis7.jpg", Imgcodecs.IMREAD_GRAYSCALE);
            Mat destination = new Mat(source.rows(), source.cols(), source.type());
            source.convertTo(destination, -1, alpha, beta);// parlaklik ayarini yapiyor
            Imgproc.GaussianBlur(source, destination, new Size(0, 0), 65);
            //                       alpha               beta  gamma
            Core.addWeighted(source, 1.5, destination, -0.75, 0, destination);
            Imgproc.resize(source, destination, new Size(1700, 1700));//resim yeniden boyutlandiriliyor
            Imgcodecs.imwrite("C:\\Users\\asus1\\Documents\\NetBeansProjects\\yazlab2.1.1deneme\\src\\yazlab2\\pkg1\\pkg1deneme\\Fisler\\output.jpg", destination);

        } catch (Exception e) {
            System.out.println("HATA : " + e.getMessage());
        }
    }

    public static void OCR() {
        File ocr_image = new File("C:\\Users\\asus1\\Documents\\NetBeansProjects\\yazlab2.1.1deneme\\src\\yazlab2\\pkg1\\pkg1deneme\\Fisler\\output.jpg");
        ITesseract fis = new Tesseract();
        fis.setLanguage("tur");
        String sonuc, tarih = null;
        String[] parse = null;
        try {
            sonuc = fis.doOCR(ocr_image);
            System.out.println(sonuc);
            parse = sonuc.split(" ");
//            for (int i = 0; i < parse.length; i++) {
//                if (parse[i].toLowerCase().contains("tarih")) {
//                    tarih.concat(parse[i + 1].substring(0, 10)); BU KISIM HATALI!!
//                    System.out.println(tarih);
//                }
//            }
        } catch (TesseractException e) {
            System.out.println("HATA : "+e.getMessage());
        }
    }

    public static void main(String[] args) {
        filter();
        OCR();
        launch(args);
    }

}
