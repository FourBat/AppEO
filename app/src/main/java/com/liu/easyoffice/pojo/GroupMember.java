package com.liu.easyoffice.pojo;

import java.util.List;

/**
 * Created by hui on 2016/9/28.
 */

public class GroupMember {
    private List<User> users;
    private List<Group> groups;
    public GroupMember() {
        // TODO Auto-generated constructor stub
    }
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }
    public List<Group> getGroups() {
        return groups;
    }
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
