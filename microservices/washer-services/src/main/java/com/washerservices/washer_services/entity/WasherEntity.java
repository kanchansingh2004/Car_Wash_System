package com.washerservices.washer_services.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "washer_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WasherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Not null first name
    @Column(nullable = false)
    private String firstName;

    //Not null second name
    @Column(nullable = false)
    private String lastName;

    //Not null role
    @Column(nullable = false)
    private String role;

    //Unique email for login
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String profileImage;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, length = 100)
    private String userId;
}
