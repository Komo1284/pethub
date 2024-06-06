package itbank.pethub.vo;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberVO {
    private int id;

    private String name;
    private String email;
    private String phone;
    private int zip_code;
    private String primary_address, address_details;
    private String userid;
    private String userpw, pwCheck;
    private String nick;
    private String profile;
    private int ad;
    private String newpw;
    private RoleVO role;


    @Builder
    public MemberVO(String name, String email,RoleVO role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public MemberVO update(String name) {
        this.name = name;
        return this;
    }
    public String getRoleKey() {
        return this.role.getKey();
    }

}
