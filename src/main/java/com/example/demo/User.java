package com.example.demo;
import java.util.Objects;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User{
    private @Id @GeneratedValue Long id;
    private String name;
    private String surname;
    private String photoUrl;
    private Integer age;
    private String email;
    private Boolean actif;
    User(){}
    User(String name,String surname,String photoUrl,Integer age,String email,Boolean actif)
    {
        this.name=name;
        this.surname=surname;
        this.photoUrl=photoUrl;
        this.age=age;
        this.email=email;
        this.actif=actif;
    }
    public void setId(Long id) {this.id = id; }
    public Long getId() { return this.id; }
    public String getName() { return this.name; }
    public String getSurname() { return this.surname; }
    public String getPhotoUrl() { return this.photoUrl; }
    public Integer getAge() { return this.age; }
    public String getEmail() { return this.email; }
    public Boolean getActif() { return this.actif; }
    public void setName(String name){ this.name=name;}
    public void setSurname(String surname){ this.surname=surname;}
    public void setPhotoUrl(String photoUrl){ this.photoUrl=photoUrl;}
    public void setAge(Integer age){ this.age=age;}
    public void setEmail(String email){ this.email=email;}
    public void setActif(Boolean actif){ this.actif=actif;}
}