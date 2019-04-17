package TSP.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class OutputUtil {
    private static PrintStream out;
    public static void setOutputFile(File file) throws FileNotFoundException {
        out=new PrintStream(new FileOutputStream(file));
    }
    public static void println(Object obj){
        System.out.println(obj);
        out.println(obj);
    }
    public static void print(Object obj){
        System.out.print(obj);
        out.print(obj);
    }
    public static void close(){
        out.close();
        out=null;
    }
}