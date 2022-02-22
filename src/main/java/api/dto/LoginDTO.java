package api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LoginDTO {
    @ApiModelProperty()
    private String username;
    @ApiModelProperty(position = 1)
    private String password;
}
