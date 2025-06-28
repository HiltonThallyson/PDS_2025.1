package br.imd.mybookplace.DTOS;

import br.imd.mybookplace.entities.User;


public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private String nickname;

    public UserInfoResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickName();
        this.username = user.getUsername();
    }

   
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getNickName() {
        return nickname;
    }
    public void setNickName(String nickname) {
        this.nickname = nickname;
    }
    
}
