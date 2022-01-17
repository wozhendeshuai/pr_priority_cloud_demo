package com.jjyu.utils;

import org.apache.commons.lang3.StringUtils;

public enum UserTeamRole {

    MEMBER("member", "本团队核心成员,可以查看所有人的信息"),
    ADMIN("admin", "本团队管理员，可以查看所有人的信息,新增，删除成员以及管理者");
    private String userRole;
    private String explain;

    private UserTeamRole(String userRole, String explain) {
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

    @Override
    public String toString() {
        return this.userRole + this.explain;
    }
}
