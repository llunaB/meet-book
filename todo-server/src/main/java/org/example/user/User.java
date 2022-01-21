package org.example.user;

import lombok.*;

import javax.persistence.*;

//import javax.persistence.*;
//import java.time.LocalDateTime;

@Data //equals, hashcode method override / toString / Getter, Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column
    private String name;

    @NonNull
    @Column
    private String password;

    @NonNull
    @Column
    private String confirm_password;

    @NonNull
    @Column
    private String nickname;

    @NonNull
    @Column
    private String email;

    @Column
    private int gender;

    @Column
    private int age;

    @Column
    private int host_point;

    @Column
    private int guest_point;

    @Column
    private String profile_image;

    @Column
    private String profile_description;

}
