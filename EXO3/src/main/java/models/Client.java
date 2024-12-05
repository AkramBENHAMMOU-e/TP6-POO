package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
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

