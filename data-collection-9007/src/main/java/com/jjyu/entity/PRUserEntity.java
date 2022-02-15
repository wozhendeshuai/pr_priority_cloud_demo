package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("pr_user")
public class PRUserEntity {
    @TableField("user_id")
    private Integer userId;

    @TableField("user_name")
    private String userName;

    @TableField("followers_num")
    private Integer followersNum;

    @TableField("followers")
    private String followers;

    @TableField("following_num")
    private Integer followingNum;

    @TableField("following")
    private String following;

    @TableField("public_repos_num")
    private Integer publicReposNum;

    @TableField("author_association_with_repo")
    private String authorAssociationWithRepo;

}
