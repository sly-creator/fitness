package nl.hu.bep.setup.security;

public record LogonRequest(String username, String password) {



    public String getUsername() {
        return username;
    }



    public String getPassword() {
        return password;
    }


}
