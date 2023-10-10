package com.cssl.playedu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData implements Serializable {
    private Integer id;
    @Email(message = "邮箱格式不正确")
    private String email;
    private String name;
    private String avatar;
    private String password;
    @NotBlank(message = "身份证号不能为空")
    @Pattern(regexp = "^\\d{17}[\\dxX]$", message = "身份证号格式不正确")
    private String id_card;
    private String dep_id;
}
