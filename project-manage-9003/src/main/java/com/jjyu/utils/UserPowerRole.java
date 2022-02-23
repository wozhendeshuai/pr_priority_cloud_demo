package com.jjyu.utils;

import org.apache.commons.lang3.StringUtils;

public enum UserPowerRole {

    repo("repo", "项目数据管理权力"),
    team("team", "对团队成员进行管理的权力"),
    model("model", "控制模型的权力");

    private String userPower;
    private String explain;

    private UserPowerRole(String userRole, String explain) {
        this.userPower = userRole;
        this.explain = explain;
    }

    public String getUserPower() {
        return userPower;
    }

    public void setUserPower(String userPower) {
        this.userPower = userPower;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public boolean equals(String role) {
        if (StringUtils.lowerCase(role).equals(this.userPower)) {
            return true;
        }
        return false;
    }

    public static boolean hasRole(String role) {
        for (UserPowerRole userRepoRole : UserPowerRole.values()) {
            if (userRepoRole.equals(role)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.userPower + this.explain;
    }
}
