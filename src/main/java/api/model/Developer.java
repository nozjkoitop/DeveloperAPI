package api.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Author : NozjkoiTop
 * @Date : Created in 12.02.2022
 */


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DEVELOPERS")
@ApiModel
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ApiModelProperty("Name is ->")
    @Size(min = 3, max = 50, message = "Minimum username length: 3 characters")
    @Column(name = "username")
    private String username;

    @ApiModelProperty("Email is ->")
    @Column(name = "email",unique = true)
    private String email;

    @ApiModelProperty("Password set to ->")
    @Size(min = 5, message = "Minimum password length: 5 characters")
    private String password;

    @ApiModelProperty("Issuer is ->")
    @Column
    private String issuer;

    @ApiModelProperty("Subject is ->")
    @Column
    private String subject;

    @ElementCollection(fetch = FetchType.EAGER)
    List<Role> roles;
}