package ua.goit.hw18restapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "seq_users_id", allocationSize = 1)
    private Long id;
    @Column(name = "username", unique = true)
    private String userName;
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    @Column(name = "role")
    private String role;
    @OneToMany(mappedBy = "user")
    List<Note> notes;
}
