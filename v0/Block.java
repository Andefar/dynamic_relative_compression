/**
 * Created by andl on 08/02/2016.
 */

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Block {

    int pos;
    int len;

    public Block(int pos, int len) {
        this.pos = pos;
        this.len = len;
    }

    public int getPos() {return this.pos;}

    public int getLength() {return this.len;}

    public void toFile(ArrayList<Block> blocks, String name) {
        List lines = new ArrayList<String>();

        for(int i = 0; i < blocks.size();i++) {
            lines.add(blocks.get(i).toString());
        }

        Path file = Paths.get(name);
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

/*    public void read(String name) throws IOException {

        for (String line : Files.readAllLines(Paths.get(name))) {

        }
    }*/

    public String toString() {
        return "(" + this.pos + "," + this.len + ")";
    }


}
