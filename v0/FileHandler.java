

import com.sun.org.apache.xml.internal.utils.StringBufferPool;
import com.sun.tools.javac.util.ArrayUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by andl on 09/02/2016.
 */
public class FileHandler {

    public FileHandler () {

    }

    public void toFile(ArrayList<Block> blocks, String name) {
        List lines = new ArrayList<String>();
        for(int i = 0; i < blocks.size();i++) {
            lines.add(toStringCompact(blocks.get(i)));
        }
        Path file = Paths.get(name);
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void toFileBinary(ArrayList<Block> blocks, String name) {
        String output = "";
        for(int i = 0; i < blocks.size();i++) {
            output += toStringCompact(blocks.get(i));
        }

        byte[] bOut = output.getBytes();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(name);
            fos.write(bOut);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Block> read(String name) throws IOException {
        ArrayList<Block> out = new ArrayList<Block>();
        for (String line : Files.readAllLines(Paths.get(name))) {
            out.add(fromString(line));
        }
        return out;
    }

    private Block fromString(String line) {
        //format="p l"
        String[] s = line.split(",");
        int p = Integer.parseInt(s[0]);
        int l = Integer.parseInt(s[1]);
        return new Block(p,l);
    }


    private String toStringCompact(Block b) {
        //format="p l"
        return "" + b.getPos()+ "," + b.getLength();
    }

    public String readFromFileLine(String path) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

        StringBuffer stringBuffer = new StringBuffer();
        String line = null;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line).append("\n");
        }

        return stringBuffer.toString();
    }

    //reads from file character by character - since the lines in the full DNA file are to long for readline
    public String readFromFileChar(String path) throws IOException{

        FileReader fileReader = new FileReader(path);

        double count = 0.0;
        double full = 52428800.0;

        String fileContents = "";

        int i;
        while ((i = fileReader.read()) != -1) {

            double percent = count/full*100.0;
            //if (((int) (percent % 5.0)) == 0 && ((int) percent) != 0) { System.out.println(percent); }

            char ch = (char) i;
            System.out.println(percent);

            fileContents = fileContents + ch;

            count = count + 1.0;
        }

        return fileContents;
    }


}
