package com.ssafy.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
    private String email;
    private String nickname;
    private String profile_image;
    private String profile_description;

    @Override
    public String toString() {
        return "SingUpUserDto{" +
                ", nickname='" + nickname + '\'' +
                ", profile_image='" + profile_image + '\'' +
                ", profile_description='" + profile_description + '\'' +
                '}';
    }
}
