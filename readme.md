# Java File Handling and Generic Programming Assignment

## Overview
This assignment covers three key exercises in Java Object-Oriented Programming (POO):
1. File Listing and Directory Exploration
2. Phone Contact Management System
3. Product and Client Management with Serialization

## Exercise 1: Directory Listing Utility
### Objective
Simulate the `ls` command to list files and directories recursively.

### Features
- Accept directory path from user input
- Recursively explore subdirectories
- Display file/directory information:
    - Type: `<DIR>` or `<FILE>`
    - Access modes:
        - 'r': Readable
        - 'w': Writable
        - 'h': Hidden
### Code
### LsProgramme Class
```java
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
```

### Example Output
```
Veuillez saisir le chemin complet du répertoire :
./fichiersDat
..\fichiersDat\clients.dat <FICH> rw-
..\fichiersDat\kda <DIR> rw-
..\fichiersDat\produits.dat <FICH> rw-
```
## Exercise 2: Phone Contact Management
### Objective
Create a phone directory management system storing contacts in separate files.

### Key Components
- `DossierContact` class managing contacts
- Menu-driven interface with options:
    1. Search telephone number
    2. Add new contact
    3. Delete contact
    4. Change contact's phone number
    5. Exit program

### Sample Code Snippet
### DossierContact Class
```java
public class DossierContact {

    Map<String,String> contacts = new HashMap<>();

    public DossierContact() {
        readDataFiles();
    }

    private void readDataFiles() {
        File dossier = new File("./contacts");
        if(dossier.exists() && dossier.isDirectory()){
            System.out.println("Dossier contacts existe");
            File[] fichiers = dossier.listFiles();
            if (fichiers != null) {
                for (File f : fichiers) {
                    try {
                        FileReader fr = new FileReader(f);
                        BufferedReader br = new BufferedReader(fr);
                        String nom = f.getName().split("\\.")[0];
                        String numTele = br.readLine();
                        contacts.put(nom,numTele);
                        br.close();
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        }
        else{
            boolean created = dossier.mkdir();
            if (created){
                System.out.println("Dossier contacts créé avec succès");
            }
            else{
                System.out.println("Erreur lors de la création du dossier contacts");
            }
        }
    }

    public void ajouterContact(String nom, String numTele){
        if (contacts.containsKey(nom)) {
            System.out.println("Contact already exists. Use changeNumber to modify.");
        } else {

            try {
                FileWriter fw = new FileWriter("./contacts/" + nom + ".txt");
                BufferedWriter bf = new BufferedWriter(fw);
                bf.write(numTele);
                System.out.println("Contact ajouté avec succès");
                bf.close();
                contacts.put(nom, numTele);
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public void changerNumero(String nom, String nouveauNumero) {
        if (contacts.containsKey(nom)) {
            contacts.replace(nom,nouveauNumero);
            contacts.forEach((k,v)->System.out.println(k+" : "+v));
            ajouterContact(nom,nouveauNumero);
            System.out.println("Numéro de téléphone modifié avec succès");
        }
        else {
            System.out.println("Contact introuvable");
        }
    }

    public void supprimerContact(String nom){
        contacts.remove(nom);
        if (new File("./contacts/"+nom+".txt").delete()){
            System.out.println("Contact supprimé avec succès");
        }
        else{
            System.out.println("Erreur lors de la suppression du contact");
        }

    }

    public void rechercherContact(String nom) {
        if (contacts.containsKey(nom)) {
            System.out.println(nom + " : " + contacts.get(nom));
        } else {
            System.out.println("Contact introuvable");
        }
    }
}

```
### Application Class
```java
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
```
### Example Output
```
Dossier contacts créé avec succès
1- Rechercher un numéro de téléphone
2- Ajouter un contact
3- Supprimer un contact
4- changer un numero de telephone 
5- Lister les contacts
6- Quitter
Faites votre choix: 
2
Nom
Akram
Numéro de téléphone :
09912331
Contact ajouté avec succès
1- Rechercher un numéro de téléphone
2- Ajouter un contact
3- Supprimer un contact
4- changer un numero de telephone 
5- Lister les contacts
6- Quitter
Faites votre choix: 
5
Akram : 09912331
1- Rechercher un numéro de téléphone
2- Ajouter un contact
3- Supprimer un contact
4- changer un numero de telephone 
5- Lister les contacts
6- Quitter
Faites votre choix: 
4
Nom
Akram
Nouveau numéro de téléphone :
00000000
Akram : 00000000
Contact already exists. Use changeNumber to modify.
Numéro de téléphone modifié avec succès
1- Rechercher un numéro de téléphone
2- Ajouter un contact
3- Supprimer un contact
4- changer un numero de telephone 
5- Lister les contacts
6- Quitter
Faites votre choix: 
5
Akram : 00000000
1- Rechercher un numéro de téléphone
2- Ajouter un contact
3- Supprimer un contact
4- changer un numero de telephone 
5- Lister les contacts
6- Quitter
Faites votre choix: 
6

Process finished with exit code 0
```


## Exercise 3: Product and Client Management with Serialization
### Objective
Develop a generic file-based management system for products and clients using serialization.

### Key Classes and Interfaces
- `Produit` and `Client` classes implementing `Serializable`
- Generic `IMetier<T>` interface with methods:
    - `add(T o)`
    - `getAll()`
    - `findByNom(String nom)`
    - `delete(String nom)`
    - `saveAll()`
- `MetierProduitImpl` and `MetierClientImpl` implementations

### Application Features
- List all items
- Search by name
- Add new items
- Delete items
- Save to files (`produits.dat`, `clients.dat`)

## Sample Code Snippet
### Model package
- Produit Class 
```java
@AllArgsConstructor @ToString
public class Produit implements Serializable{
    @Getter @Setter
    private String nom;
    @Getter @Setter
    private double prix;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private int nbr_stock;

}

```
- Client Class
```java
@AllArgsConstructor @ToString
public class Client  implements Serializable {

    @Getter @Setter
    private String nom;
    @Getter @Setter
    private String prenom;
    @Getter @Setter
    private String adresse;
    @Getter @Setter
    private String tel;
    @Getter @Setter
    private String email;
}

```

### dao package
- IMetier Interface
```java
public interface IMetier<T> {
    public T add(T o);
    public List<T> getAll();
    public T findByNom(String nom);
    public void delete(String nom);
    public void saveAll();
}
```
- MetierProduitImpl Class
```java
public class MetierProduitImp implements IMetier<Produit>{
   private List<Produit> produits = new ArrayList<>();
   Produit p1 = new Produit("okkk",12,"olsm",1);

    private final String nomFile = "produits.dat";

    @Override
    public Produit add(Produit o) {
        produits.add(o);
        System.out.println(o.getNom() +" ajouté avec succès");
        return o;
    }

    @Override
    public List<Produit> getAll() {
        try {
        produits.clear();
        File file = new File(nomFile);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while (true) {
                if ((line = bufferedReader.readLine()) == null) break;
            String[] values = line.split("/");
            Produit produit = new Produit(values[0], Double.parseDouble(values[1]), values[2], Integer.parseInt(values[3]));
            produits.add(produit);
        }

        bufferedReader.close();
        return produits;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Produit findByNom(String nom) {
        Produit prod= produits.stream().filter(produit -> produit.getNom().equals(nom)).findFirst().orElse(null);
        if (prod==null){
            System.out.println(nom+" n'existe pas");
        }
        return prod;
    }

    @Override
    public void delete(String nom) {
        boolean supp = produits.removeIf(produit -> produit.getNom().equals(nom));
        if(supp){
            System.out.println("Produit supprimé avec succès");
        }
        else {
            System.out.println("ce produit n'existe pas !");
        }
    }
    public void saveAll() {
        try {
            FileWriter fileWriter = new FileWriter(nomFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Produit p : produits) {
                bufferedWriter.write(p.getNom()+ "/" + p.getPrix() + "/"+ p.getDescription()  + "/" + p.getNbr_stock());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
```
- MetierClientImpl Class
```java
public class MetierClientImp implements IMetier<Client>{
    List<Client> clients=new ArrayList<>();
    final String nameFile = "./fichiersDat/clients.dat";
    @Override
    public Client add(Client o) {
        clients.add(o);
        System.out.println(o.getNom() +" ajouté avec succès");
        return o;
    }

    @Override
    public List<Client> getAll(){
        try {
            clients.clear();
            File newFile = new File(nameFile);
            FileReader fileReader = new FileReader(newFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split("/");
                Client client = new Client(values[0], values[1], values[2], values[3], values[4]);
                clients.add(client);
            }
            bufferedReader.close();

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return clients;
    }

    @Override
    public Client findByNom(String nom) {
        Client cl=clients.stream().filter(client -> client.getNom().equals(nom)).findFirst().orElse(null);
        if (cl==null){
            System.out.println(nom+" n'existe pas");
        }
        return cl;
    }

    @Override
    public void delete(String nom) {
       boolean supp = clients.removeIf(client -> client.getNom().equals(nom));
        if(supp){
            System.out.println("Client supprimé avec succès");
        }
        else {
            System.out.println(nom+" n'existe pas !");
        }
    }

    @Override
    public void saveAll() {
        try {
            File newFile = new File(nameFile);
            FileWriter fileWriter = new FileWriter(newFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Client c : clients) {
                bufferedWriter.write(c.getNom() + "/" + c.getPrenom() + "/" + c.getAdresse() + "/" + c.getTel() + "/" + c.getEmail());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
```
### application pack
- ApplicationClient Class
```java
public class ApplicationClient {
    public static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);
        IMetier mp = new MetierClientImp();
        int continuer;
        do{
            System.out.println("Menu : ");
            System.out.println("1. Afficher la liste des clients.\n" +
                    "2. Rechercher un client par son nom.\n" +
                    "3. Ajouter un nouveau client dans la liste.\n" +
                    "4. Supprimer un client par nom.\n" +
                    "5. Sauvegarder les clients : cette option permet de sauvegarder la liste des\n" +
                    "clients dans fichier nommé clients.dat.\n" +
                    "6. Quitter ce programme.");
            System.out.println("Votre choix (Numéro) : ");
            int i=sc.nextInt();
            sc.nextLine();
            switch (i){
                case 1 :
                    System.out.println("Les clients : "+mp.getAll());
                    break;
                case 2 :
                    System.out.println("Entrer un nom : ");
                    String mtcl = sc.nextLine();
                    System.out.println(mp.findByNom(mtcl));
                    break;
                case 3 :
                    System.out.println("Entrer le nom : ");
                    String nom = sc.nextLine();
                    System.out.println("entrer le prenom: ");
                    String prenom = sc.nextLine();
                    System.out.println("entrer l'adresse: ");
                    String adresse = sc.nextLine();
                    System.out.println("entrer le num de telephone : ");
                    String tel = sc.nextLine();
                    System.out.println("entrer l'email : ");
                    String email = sc.nextLine();
                    Client p = new Client(nom,adresse,prenom,tel,email);
                    mp.add(p);
                    break;
                case 4 :
                    System.out.println("donner le nom du client à supprimer : ");
                    String nm = sc.nextLine();
                    mp.delete(nm);
                    System.out.println("Client suppimé avec succès ");
                    break;
                case 5 :
                    mp.saveAll();
                    break;
                default:
                    System.out.println("Choix invalide");
            }
            System.out.println("Voulez vous rechoisir : NON(0) OUI(1)");
            continuer = sc.nextInt();
            sc.nextLine();

        }while (continuer==1);
    }

}
```
- ApplicationProduit Class
```java
public class ApplicationProduit {

    public static void main(String[] args) {
            Scanner sc  = new Scanner(System.in);
            IMetier mp = new MetierProduitImp();
            int continuer=0;
            do{
                System.out.println("Menu : ");
                System.out.println("1. Afficher la liste des produits.\n" +
                        "2. Rechercher un produit par son nom.\n" +
                        "3. Ajouter un nouveau produit dans la liste.\n" +
                        "4. Supprimer un produit par nom.\n" +
                        "5. Sauvegarder les produits : cette option permet de sauvegarder la liste des\n" +
                        "produits dans fichier nommé produits.dat.\n" +
                        "6. Quitter ce programme.");
                System.out.println("Votre choix (Numéro) : ");
                int i=sc.nextInt();
                sc.nextLine();
                switch (i){
                    case 1 :
                        System.out.println("Les produits : "+mp.getAll());
                        break;
                    case 2 :
                        System.out.println("Entrer un mot clé : ");
                        String mtcl = sc.nextLine();
                        System.out.println(mp.findByNom(mtcl));
                        break;
                    case 3 :
                        System.out.println("Entrer le nom de nouveu produit : ");
                        String nom = sc.nextLine();
                        System.out.println("entrer son prix: ");
                        double prx = sc.nextDouble();
                        sc.nextLine();
                        System.out.println("entrer une description : ");
                        String desc = sc.nextLine();
                        System.out.println("entrer le nombre de stock : ");
                        int nbrStck = sc.nextInt();
                        sc.nextLine();
                        Produit p = new Produit(nom,prx,desc,nbrStck);
                        mp.add(p);
                        break;
                    case 4 :
                        System.out.println("donner le nom du produit à supprimer : ");
                        String nm = sc.nextLine();
                        mp.delete(nm);
                        break;
                    case 5 :
                        mp.saveAll();
                        break;
                    default:
                        System.out.println("Choix invalide");
                }
                System.out.println("Voulez vous rechoisir : NON(0) OUI(1)");
                continuer = sc.nextInt();
                sc.nextLine();

            }while (continuer==1);
    }
}
```
### Example Output
```
Menu : 
1. Afficher la liste des clients.
2. Rechercher un client par son nom.
3. Ajouter un nouveau client dans la liste.
4. Supprimer un client par nom.
5. Sauvegarder les clients : cette option permet de sauvegarder la liste des
clients dans fichier nommé clients.dat.
6. Quitter ce programme.
Votre choix (Numéro) : 
1
Les clients : [Client(nom=AAZA, prenom=AZhhsj, adresse=AEER, tel=122111, email=lsmommq@,jsks.mo)]
Voulez vous rechoisir : NON(0) OUI(1)
1
Menu : 
1. Afficher la liste des clients.
2. Rechercher un client par son nom.
3. Ajouter un nouveau client dans la liste.
4. Supprimer un client par nom.
5. Sauvegarder les clients : cette option permet de sauvegarder la liste des
clients dans fichier nommé clients.dat.
6. Quitter ce programme.
Votre choix (Numéro) : 
3
Entrer le nom : 
IIIIP
entrer le prenom: 
OOP
entrer l'adresse: 
DEE
entrer le num de telephone : 
33221
entrer l'email : 
eioo@oom.p
IIIIP ajouté avec succès
Voulez vous rechoisir : NON(0) OUI(1)
1
Menu : 
1. Afficher la liste des clients.
2. Rechercher un client par son nom.
3. Ajouter un nouveau client dans la liste.
4. Supprimer un client par nom.
5. Sauvegarder les clients : cette option permet de sauvegarder la liste des
clients dans fichier nommé clients.dat.
6. Quitter ce programme.
Votre choix (Numéro) : 
5
Clients sauvegardés avec succès
Voulez vous rechoisir : NON(0) OUI(1)
1
Menu : 
1. Afficher la liste des clients.
2. Rechercher un client par son nom.
3. Ajouter un nouveau client dans la liste.
4. Supprimer un client par nom.
5. Sauvegarder les clients : cette option permet de sauvegarder la liste des
clients dans fichier nommé clients.dat.
6. Quitter ce programme.
Votre choix (Numéro) : 
1
Les clients : [Client(nom=AAZA, prenom=AZhhsj, adresse=AEER, tel=122111, email=lsmommq@,jsks.mo), Client(nom=IIIIP, prenom=DEE, adresse=OOP, tel=33221, email=eioo@oom.p)]
Voulez vous rechoisir : NON(0) OUI(1)
1
Menu : 
1. Afficher la liste des clients.
2. Rechercher un client par son nom.
3. Ajouter un nouveau client dans la liste.
4. Supprimer un client par nom.
5. Sauvegarder les clients : cette option permet de sauvegarder la liste des
clients dans fichier nommé clients.dat.
6. Quitter ce programme.
Votre choix (Numéro) : 
4
donner le nom du client à supprimer : 
AAZA
Client supprimé avec succès
```

## Requirements
- Java Development Kit (JDK)
- Basic understanding of:
    - Object-Oriented Programming
    - File I/O
    - Generics
    - Serialization

## Learning Outcomes
- File manipulation techniques
- Generic programming
- Object serialization
- Menu-driven console applications
