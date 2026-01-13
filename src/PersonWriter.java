import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class PersonWriter {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        ArrayList<String> recs = new ArrayList<>();

        String id;
        String firstName;
        String lastName;
        String title;
        int yob;

        String rec;

        boolean done = false;

        do {
            id = SafeInput.getNonZeroLenString(in, "Enter the ID");
            firstName = SafeInput.getNonZeroLenString(in, "Enter the First Name");
            lastName = SafeInput.getNonZeroLenString(in, "Enter the Last Name");
            title = SafeInput.getNonZeroLenString(in, "Enter the Title");
            yob = SafeInput.getInt(in, "Enter the Year of Birth");

            rec = id + "," + lastName + "," + firstName + "," + title + "," + yob;

            recs.add(rec);
            done = SafeInput.getYNConfirm(in, "Are you done (Y/N)?");

        } while(!done);

        File workingDirectory = new File(System.getProperty("user.dir"));
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(workingDirectory);
        File selectedFile;
        try
        {
            chooser.setCurrentDirectory(workingDirectory);

            // write collected records to a file named `recs` under the project `src` directory
            Path outFile = workingDirectory.toPath().resolve("src").resolve("recs");
            if (outFile.getParent() != null) {
                Files.createDirectories(outFile.getParent()); // ensure `src` exists
            }
            Files.write(outFile, recs, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Wrote " + recs.size() + " records to " + outFile);

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();
                InputStream fileIn = new BufferedInputStream(Files.newInputStream(file, StandardOpenOption.CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileIn));

                int line = 0;
                while(reader.ready())
                {
                    rec = reader.readLine();
                    line++;
                    System.out.printf("\nLine %4d %-60s ", line, rec);
                }
                reader.close();
                System.out.println("\n\nData file read!");
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found!!!");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
