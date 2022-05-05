package com.bolshakov.diploma;

public class User {
    public String userName;
    public String email;
    public String membership;

    public User() {

    }


    public void setMembership(String membership) {
        this.membership = membership;
    }



    public User(String userName, String email, String membership) {
        this.userName = userName;
        this.email = email;
        this.membership = membership;
    }



    public void setUserName(String userName) {
        this.userName = userName;
    }



    public void setEmail(String email) {
        this.email = email;
    }



    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", membership='" + membership + '\'' +
                '}';
    }
}
