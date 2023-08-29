import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class ProductWriter {

    static ArrayList<String> list = new ArrayList<>();
    static Scanner console = new Scanner(System.in);

    public static void main(String[] args) {

        boolean done = false;

        do {

            String ID = SafeInput.getRegExString(console, "Enter the ID of the product", "^[0-9]{6}$");
            String name = SafeInput.getNonZeroLenString(console, "Enter the first name of the product");
            String description = SafeInput.getNonZeroLenString(console, "Enter the description of the product");
            double cost = SafeInput.getRangedDouble(console, "Enter the cost of the product", 0, 1000000);

            String record = String.join(", ", ID, name, description, Double.toString(cost));

            list.add(record);

            done = !(SafeInput.getYNConfirm(console,"Do you want to enter another person?"));

            if (done) {
                String fileName = SafeInput.getNonZeroLenString(console, "Enter a name for a file that saves your list");

                File workingDirectory = new File(System.getProperty("user.dir"));

                Path file;

                file = Paths.get(workingDirectory.getPath()).resolve(fileName);

                try {
                    File userFile = new File((file.getFileName().toString()));
                    if (userFile.createNewFile()) {
                        try {

                            Files.newBufferedWriter(file, TRUNCATE_EXISTING);
                            Files.newInputStream(file, TRUNCATE_EXISTING);

                            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

                            for (String line: list)
                            {
                                writer.write(line, 0, line.length());

                                writer.newLine();
                            }
                            writer.close();

                            System.out.println("\nThe list has been saved to the file " + file.getFileName() + "!");
                        }

                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }

                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

            }

        } while (!done);

    }
}