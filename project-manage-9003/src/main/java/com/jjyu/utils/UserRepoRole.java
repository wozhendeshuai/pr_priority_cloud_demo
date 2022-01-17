package com.jjyu.utils;

import org.apache.commons.lang3.StringUtils;

public enum UserRepoRole {

    GUEST("guest", "访客非本代码仓成员可以创建，查看，评论PR"),
    CONTRIBUTOR("contributor", "本代码仓贡献者可以创建，查看，查看自己的，评论PR"),
    MEMBER("member", "本代码仓核心成员,可以创建，查看所有的，评论，评审PR"),
    ADMIN("admin", "本代码仓管理员，可以创建，查看所有的，评论，评审PR，同时可以更新模型");
    private String userRole;
    private String explain;

    private UserRepoRole(String userRole, String explain) {
        this.userRole = userRole;
        this.explain = explain;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public boolean equals(String role) {
        if (StringUtils.lowerCase(role).equals(this.userRole)) {
            return true;
        }
        return false;
    }

    public static boolean hasRole(String role) {
        for (UserRepoRole userRepoRole : UserRepoRole.values()) {
            if (userRepoRole.equals(role)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.userRole + this.explain;
    }
}
