import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andl on 09/02/2016.
 */
public class FileHandler {

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

    public List<Block> read(String name) throws IOException {
        List out = new ArrayList<Block>();
        for (String line : Files.readAllLines(Paths.get(name))) {
            out.add(fromString(line));
        }
        return out;
    }

    private Block fromString(String line) {
        //format="p l"
        String[] s = line.split(" ");
        int p = Integer.parseInt(s[0]);
        int l = Integer.parseInt(s[1]);
        return new Block(p,l);
    }

    private String toStringCompact(Block b) {
        //format="p l"
        return "" + b.getPos() + " " + b.getLength();
    }

}
