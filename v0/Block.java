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

    public String toString() {
        return "(" + this.pos + "," + this.len + ")";
    }


}
