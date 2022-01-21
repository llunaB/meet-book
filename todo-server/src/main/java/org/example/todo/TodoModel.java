package org.example.todo;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data //equals, hashcode method override / toString / Getter, Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TodoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column //(nullable = false)
    private String title;

    @NonNull
    @Column(name = "todoOrder") //(nullable = false)
    private Long order;

    @NonNull
    @Column //(nullable = false)
    private Boolean complete;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
