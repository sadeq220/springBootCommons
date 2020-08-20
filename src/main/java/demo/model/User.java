package demo.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "uname")
    private String userName;

    private String passwd;

//    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY
//    ,mappedBy = "user"
//    ,orphanRemoval = true)
   // @Transient
   // private List<Message> messages;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
