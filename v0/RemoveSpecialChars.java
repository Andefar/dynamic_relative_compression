import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andl on 21/04/2016.
 */
public class RemoveSpecialChars {

    public static void main(String[] args) {

        Map<String,String> names = new HashMap<>();
        FileHandler f = new FileHandler();
        names.put("/Users/AndreasLauritzen/dynamic_relative_compression/dna.50mb","dna_clean.50mb");
        names.put("/Users/AndreasLauritzen/dynamic_relative_compression/dna.100mb","dna_clean.100mb");
        names.put("/Users/AndreasLauritzen/dynamic_relative_compression/dna.200mb","dna_clean.200mb");
        names.put("/Users/AndreasLauritzen/dynamic_relative_compression/dna.400mb","dna_clean.400mb");

        for (Map.Entry<String, String> file : names.entrySet()) {
            try {
                String toWrite = f.readFromFileLine(file.getKey()).replaceAll("[^ACGT]","");
                File fa = new File(file.getValue());
                FileWriter fileWriter = new FileWriter(fa);
                fileWriter.write(toWrite);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
