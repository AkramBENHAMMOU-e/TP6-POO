import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class LsProgramme {

    public static void main(String[] args) throws IOException {
        System.out.println("Veuillez saisir le chemin complet du répertoire :");
        Scanner scanner = new java.util.Scanner(System.in);
        String chemin = scanner.nextLine();
        scanner.close();
        listerRepertoire(chemin);

    }
    public static void listerRepertoire(String directoryPath) throws IOException {
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            System.err.println("Le répertoire n'existe pas.");
            return;
        }

        if (!directory.isDirectory()) {
            System.err.println("Ce n'est pas un répertoire.");
            return;
        }

        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                String type = file.isDirectory() ? "<DIR>" : "<FICH>";
                String permissions = "";
                if (Files.isReadable(file.toPath())) {
                    permissions += "r";
                } else {
                    permissions += "-";
                }

                if (Files.isWritable(file.toPath())) {
                    permissions += "w";
                } else {
                    permissions += "-";
                }


                if (Files.isHidden(file.toPath())) {
                    permissions += "h";
                } else {
                    permissions += "-";
                }
                System.out.println(".." + file.getPath().substring(1)+ " " + type + " " + permissions);


                if (file.isDirectory()) {
                    listerRepertoire(file.getPath());
                }
            }
        }
    }

    }
