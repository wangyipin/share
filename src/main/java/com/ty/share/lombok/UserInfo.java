package com.ty.share.lombok;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description: 用户信息类
 * @author: 04637
 * @date: 2019/3/17
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class UserInfo {

    private String username;

    private String userCode;

    private String realName;

}
