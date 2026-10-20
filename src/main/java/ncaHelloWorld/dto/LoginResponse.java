
package ncaHelloWorld.dto;

public class LoginResponse {

    private String message;
    private String token;
    private String tokenType;
    private String email;
    private String role;

    public LoginResponse() {
    }

    public LoginResponse(
            String message,
            String token,
            String tokenType,
            String email,
            String role) {

        this.message = message;
        this.token = token;
        this.tokenType = tokenType;
        this.email = email;
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
