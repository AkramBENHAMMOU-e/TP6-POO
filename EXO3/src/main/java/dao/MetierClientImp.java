package dao;

import models.Client;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
