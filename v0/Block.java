/**
 * Created by andl on 08/02/2016.
 */

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Block {

    private int pos;
    private int len;

    public Block(int pos, int len) {
        this.pos = pos;
        this.len = len;
    }

    public int getPos() {return this.pos;}

    public int getLength() {return this.len;}

    public void toFile(ArrayList<Block> blocks, String name) {
        List lines = new ArrayList<String>();
        for(int i = 0; i < blocks.size();i++) {
            lines.add(blocks.get(i).toStringCompact());
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

    public String toString() {
        return "(" + this.pos + "," + this.len + ")";
    }

    private String toStringCompact() {
        //format="p l"
        return "" + this.pos + " " + this.len;
    }

    private Block fromString(String line) {
        //format="p l"
        String[] s = line.split(" ");
        int p = Integer.parseInt(s[0]);
        int l = Integer.parseInt(s[1]);
        return new Block(p,l);
    }


}
