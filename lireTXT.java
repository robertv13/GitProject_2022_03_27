import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class lireTXT {
    public static void main(String[] args) throws Exception {
        List<String> arrayTXT = new ArrayList<String>();
        FileReader fr = new FileReader("achats.txt");
        BufferedReader br = new BufferedReader(fr);
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            arrayTXT.add(line);
        }

        System.out.println(arrayTXT.size());
        String ligne = arrayTXT.get(0);
        System.out.println(ligne);
        List<String> result = Arrays.asList(ligne.split("\\s*,\\s*"));
        System.out.println(result);

        br.close();
        fr.close();
    }
}