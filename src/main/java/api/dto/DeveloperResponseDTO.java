package api.dto;

import api.model.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DeveloperResponseDTO {
    @ApiModelProperty(position = 3)
    List<Role> roles;
    @ApiModelProperty()
    private Integer id;
    @ApiModelProperty(position = 1)
    private String username;
    @ApiModelProperty(position = 2)
    private String email;
}
