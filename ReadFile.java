import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ReadFile {
    private BufferedReader br;
    private InputStreamReader ipsr;
    private InputStream ips;

    public ReadFile(String fichier) throws FileNotFoundException {
        ips = new FileInputStream(fichier);
        ipsr = new InputStreamReader(ips);
        br = new BufferedReader(ipsr);
    }

    public Init getDataFile() {
        List<Integer> init = new ArrayList<Integer>();
        List<Integer> target = new ArrayList<Integer>();
        int nbOfLine = 0;

        // lecture du fichier texte
        try {
            String ligne = br.readLine();
            nbOfLine = Integer.parseInt(ligne.trim());
            int nbLineActu = 0;

            while (nbLineActu < nbOfLine && (ligne = br.readLine()) != null) {
                List<String> chars = new ArrayList<String>(Arrays.asList(ligne.split("\\s+")));
                for (String c : chars) {
                    init.add(Integer.parseInt(c));
                }
                nbLineActu++;
            }

            nbLineActu = 0;
            while (nbLineActu < nbOfLine && (ligne = br.readLine()) != null) {
                List<String> chars = new ArrayList<String>(Arrays.asList(ligne.split("\\s+")));
                for (String c : chars) {
                    target.add(Integer.parseInt(c));
                }
                nbLineActu++;
            }

            br.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return new ReadFile.Init(nbOfLine, init, target);
    }

    public class Init {
        public final int size;
        public final List<Integer> init;
        public final List<Integer> target;

        public Init(int s, List<Integer> i, List<Integer> t) {
            this.size = s;
            this.init = i;
            this.target = t;
        }
    }
}
