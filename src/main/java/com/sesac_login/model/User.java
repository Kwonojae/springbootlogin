package com.sesac_login.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private Boolean enabled;

    @JsonIgnore
    @ManyToMany//양방향 맵핑
    @JoinTable(
            name = "user_role",     //	조인 테이블 명
            joinColumns = @JoinColumn(name = "user_id"),        //현재 엔티티를 참조하는 외래키
            inverseJoinColumns = @JoinColumn(name = "role_id"))     //반대방향 엔티티를 참조하는 외래키
    private List<Role> roles  = new ArrayList<>();//roles컬럼이름이됨 기본값이 널이되게되면 불필요한 널포인터 가 자주 발생함
}
