package com.bolshakov.diploma;

public class User {
    public String email;
    public String membership;

    public User() {

    }


    public void setMembership(String membership) {
        this.membership = membership;
    }



    public User(String email, String membership) {
        this.email = email;
        this.membership = membership;
    }






    public void setEmail(String email) {
        this.email = email;
    }



    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", membership='" + membership + '\'' +
                '}';
    }
}
