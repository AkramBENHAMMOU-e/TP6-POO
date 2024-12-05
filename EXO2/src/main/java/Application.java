import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        DossierContact dossier = new DossierContact();
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("1- Rechercher un numéro de téléphone");
            System.out.println("2- Ajouter un contact");
            System.out.println("3- Supprimer un contact");
            System.out.println("4- changer un numero de telephone ");
            System.out.println("5- Lister les contacts");
            System.out.println("6- Quitter");
            System.out.println("Faites votre choix: ");
            int choix = scanner.nextInt();
            scanner.nextLine();
            switch (choix){
                case 1:
                    System.out.println("Nom");
                    String nom = scanner.nextLine();
                    dossier.rechercherContact(nom);
                    break;
                case 2:
                    System.out.println("Nom");
                    String nom2 = scanner.nextLine();
                    System.out.println("Numéro de téléphone :");
                    String numTele = scanner.nextLine();
                    dossier.ajouterContact(nom2,numTele);
                    break;
                case 3:
                    System.out.println("Nom");
                    String nom3 = scanner.nextLine();
                    dossier.supprimerContact(nom3);
                    break;
                case 4:
                    System.out.println("Nom");
                    String nom4 = scanner.nextLine();
                    System.out.println("Nouveau numéro de téléphone :");
                    String numTele1 = scanner.nextLine();
                    dossier.changerNumero(nom4,numTele1);
                    break;
                case 5:
                    dossier.contacts.forEach((k,v)->System.out.println(k+" : "+v));
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Choix invalide");
            }
        }


    }
}
