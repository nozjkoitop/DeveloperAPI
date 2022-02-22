package api.dto;

import api.model.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Getter
public class DevDataDTO {

    @ApiModelProperty(position = 3)
    List<Role> roles;
    @ApiModelProperty(position = 4)
    String issuer;
    @ApiModelProperty(position = 5)
    String subject;
    @ApiModelProperty()
    private String username;
    @ApiModelProperty(position = 1)
    private String email;
    @ApiModelProperty(position = 2)
    private String password;

}
