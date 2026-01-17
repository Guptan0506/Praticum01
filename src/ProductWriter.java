import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class ProductWriter {
    public static void main(String[] args) {
        /*
        Here's the data that will go inside the ProductTestData.txt file:
        000001, Pipeweed, Long Bottom Leaf, 600.0
        000002, Lembas, Elven Wayfare Bread, 200.0
        000003, Wine, Woodland Elf Wine, 400.0
        000004, Mushrooms, Farmer Took’s Finest, 125.0
        000005, Mithril, Enchanted Dwarven Armor, 3000.0
         */
        String ID = "";
        String name = "";
        String desc = "";
        double cost = 0.0;
        String csvRec = "";
        boolean done = false;

        Scanner in = new Scanner(System.in);

        ArrayList<String> recs = new ArrayList<>();

        // Loop and collect data for the Person records into the array list
        do {


            // get the five data fields

            ID = SafeInput.getNonZeroLenString(in, "Enter the ID");
            name = SafeInput.getNonZeroLenString(in, "Enter the name");
            desc = SafeInput.getNonZeroLenString(in, "Enter the description");
            cost = SafeInput.getDouble(in, "Enter the cost");

            // combine them into a single csv record
            csvRec = ID + ", " + name + ", " + desc + ", " + cost;

            // add it to the ArrayList
            recs.add(csvRec);

            // Prompt user for additional records
            done = SafeInput.getYNConfirm(in, "Are you done");
        } while (!done);

        // Add the code to save the data to disk

        // uses a fixed known path:
        //  Path file = Paths.get("c:\\My Documents\\data.txt");

        // use the toolkit to get the current working directory of the IDE
        // will create the file within the Netbeans project src folder
        // (may need to adjust for other IDE)
        // Not sure if the toolkit is thread safe...
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\ProductTestData.txt");

        try {
            // Typical java pattern of inherited classes
            // we wrap a BufferedWriter around a lower level BufferedOutputStream
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            // Finally can write the file LOL!

            for (String rec : recs) {
                writer.write(rec, 0, rec.length());  // stupid syntax for write rec
                // 0 is where to start (1st char) the write
                // rec. length() is how many chars to write (all)
                writer.newLine();  // adds the new line

            }
            writer.close(); // must close the file to seal it and flush buffer
            System.out.println("Data file written!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Dumpt the array list for inspedction
//        for(String rec : recs)
//        {
//            System.out.println(rec);
//        }

    }
}