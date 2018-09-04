package manuelocc.controller;

import manuelocc.model.ColourCretors.ColourCreator;
import manuelocc.model.ColourManager;
import manuelocc.model.Mandelbrot;
import manuelocc.model.MandelbrotInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * Class to save files to the disk:
 * can save images and infos about the Mandelbrot position.
 * Can load the saved infos into a new Mandelbrot object.
 */
public class StorageManager {

    public static String delimInfo = ";";

    /**
     * Save all the image of Mandelbrot and Infos in a zip file
     */
    public static void saveZipToDisk(String path, Mandelbrot m){
        StringBuilder sb = new StringBuilder();
        sb.append("Test String");

        try {
            File f = new File(path + "\\" + generateDateName() + ".zip");
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
            ZipEntry entryInfo = new ZipEntry("info.txt");
            out.putNextEntry(entryInfo);
            File fileInfo = saveInfoToDisk(path, m);
            Files.copy(fileInfo.toPath(), out);
            out.closeEntry();
            fileInfo.delete();

            ColourCreator oldC = m.getColourCreator();
            for (ColourCreator c : ColourManager.ColourCreators) {
                m.setColourCreator(c);
                try {
                    ZipEntry entryColouredImage = new ZipEntry(c.toString() + ".png");
                    out.putNextEntry(entryColouredImage);
                    File fileImage = saveImageToDisk(path, m.getMandelbrotImage());
                    Files.copy(fileImage.toPath(), out);
                    out.closeEntry();
                    fileImage.delete();
                } catch (IOException e1) {
                    System.err.println("error saving image to : " + path);
                }
            }

            out.close();
        }catch (Exception e){

        }
    }

    /**
     * Save the infos of the current state in a txt file, to be reloaded later
     */
    public static File saveInfoToDisk(String path, Mandelbrot m){
        try{
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            File outputfile = new File(path + "\\"+generateDateName()+".txt");

            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(outputfile));
            outputStream.flush();
            MandelbrotInfo info = m.getInfo();
            outputStream.writeObject(info);
            outputStream.flush();
            outputStream.close();
            return outputfile;

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("error saving info to : "+path);
            return null;
        }
    }

    /**
     * Save the image to a file
     */
    public static File saveImageToDisk(String path, BufferedImage bi){
        try {
            File outputfile = new File(path + "\\"+generateDateName()+".png");
            ImageIO.write(bi, "png", outputfile);
            return outputfile;
        } catch (IOException e) {
            System.err.println("error saving image to : "+path);
            return null;
        }
    }

    /**
     * Load a Mandelbrot position from a previously saved file
     */
    public static Mandelbrot loadInfoFromDisk(String path){
        try {
            File info = new File(path);
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(info))) {
                return new Mandelbrot((MandelbrotInfo) objectInputStream.readObject());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Generates a file name from the date and time
     */
    private static String generateDateName() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String str = dateFormat.format(date);
        str = str.replace("/", "");
        str = str.replace(":", "");
        str = str.replace(" ", "");
        str = str + Math.round(Math.random()*100);
        return str;
    }
}
