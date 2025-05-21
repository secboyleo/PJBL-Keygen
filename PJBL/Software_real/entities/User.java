package Software_real.entities;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private String nickname;
    private String product;

    public User(String email, String nickname, String product){
        this.email = email;
        this.nickname = nickname;
        this.product = product;
    }

    public String getEmail(){ return this.email; }
    public String getNickname(){ return  this.nickname; }
    public String getProduct(){ return this.product; }
}
