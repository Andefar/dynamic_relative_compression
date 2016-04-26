
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by andl on 09/02/2016.
 */
public class FileHandler {

    File fil;

    public FileHandler() {}

    public void toFile(ArrayList<Block> blocks, String name) {
        List lines = new ArrayList<String>();
        for(int i = 0; i < blocks.size();i++) {
            lines.add(toBlockString(blocks.get(i)));
        }
        Path file = Paths.get(name);
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void write(List<String> records, Writer writer) throws IOException {
        for (String record: records) {
            writer.write(record);
        }
        writer.flush();
        writer.close();
    }
    public File getFile(){
        return this.fil;
    }

    public void stringToFile(BinaryTree bt,String name,String path) {

        try {
            File file = File.createTempFile(name, ".cmp",new File(path));
            this.fil = file;
            FileWriter writer = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(writer, 8192);
            List<String> records = bt.getListFromDPS();
            write(records, bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Block> read(String name) throws IOException {
        ArrayList<Block> out = Files.readAllLines(Paths.get(name)).stream().map(this::fromString).collect(Collectors.toCollection(ArrayList::new));
        return out;
    }

    private Block fromString(String line) {
        //format="p,l"
        String[] s = line.split(",");
        int p = Integer.parseInt(s[0]);
        int l = Integer.parseInt(s[1]);
        return new Block(p,l);
    }


    private String toBlockString(Block b) {
        //format="p,l"
        return b.getPos()+ "," + b.getLength();
    }

    public String readFromFileLine(String path) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

        StringBuffer stringBuffer = new StringBuffer();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line).append("\n");
        }

        return stringBuffer.toString();
    }



}
