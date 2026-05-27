/*
 * Decompiled with CFR 0.151.
 */
package helpz;

import helpz.Utilz;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import objects.PathPoint;

public class LoadSave {
    public static String homePath = System.getProperty("user.home");
    public static String saveFolder = "TDTutorial";
    public static String levelFile = "level.txt";
    public static String filePath = String.valueOf(homePath) + File.separator + saveFolder + File.separator + levelFile;
    private static File lvlFile = new File(filePath);

    public static void CreateFolder() {
        File folder = new File(String.valueOf(homePath) + File.separator + saveFolder);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    public static BufferedImage getSpriteAtlas() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("spriteatlas.png");
        try {
            img = ImageIO.read(is);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public static void CreateLevel(int[] idArr) {
        if (lvlFile.exists()) {
            System.out.println("File: " + lvlFile + " already exists!");
            return;
        }
        try {
            lvlFile.createNewFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        LoadSave.WriteToFile(idArr, new PathPoint(0, 0), new PathPoint(0, 0));
    }

    private static void WriteToFile(int[] idArr, PathPoint start, PathPoint end) {
        try {
            PrintWriter pw = new PrintWriter(lvlFile);
            int[] nArray = idArr;
            int n = idArr.length;
            int n2 = 0;
            while (n2 < n) {
                Integer i = nArray[n2];
                pw.println(i);
                ++n2;
            }
            pw.println(start.getxCord());
            pw.println(start.getyCord());
            pw.println(end.getxCord());
            pw.println(end.getyCord());
            pw.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void SaveLevel(int[][] idArr, PathPoint start, PathPoint end) {
        if (!lvlFile.exists()) {
            System.out.println("File: " + lvlFile + " does not exists! ");
            return;
        }
        LoadSave.WriteToFile(Utilz.TwoDto1DintArr(idArr), start, end);
    }

    private static ArrayList<Integer> ReadFromFile() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        try {
            Scanner sc = new Scanner(lvlFile);
            while (sc.hasNextLine()) {
                list.add(Integer.parseInt(sc.nextLine()));
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<PathPoint> GetLevelPathPoints() {
        if (lvlFile.exists()) {
            ArrayList<Integer> list = LoadSave.ReadFromFile();
            ArrayList<PathPoint> points = new ArrayList<PathPoint>();
            points.add(new PathPoint(list.get(400), list.get(401)));
            points.add(new PathPoint(list.get(402), list.get(403)));
            return points;
        }
        System.out.println("File: " + lvlFile + " does not exists! ");
        return null;
    }

    public static int[][] GetLevelData() {
        if (lvlFile.exists()) {
            ArrayList<Integer> list = LoadSave.ReadFromFile();
            return Utilz.ArrayListTo2Dint(list, 20, 20);
        }
        System.out.println("File: " + lvlFile + " does not exists! ");
        return null;
    }
}

