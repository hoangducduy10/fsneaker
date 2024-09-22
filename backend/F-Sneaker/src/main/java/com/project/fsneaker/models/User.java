package com.project.fsneaker.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname", length = 100)
    private String fullname;

    @Column(name = "phone_number", length = 10, nullable = false)
    private String phoneNumber;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "password", length = 200, nullable = false)
    private String password;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "facebook_account_id")
    private int facebookAccoutId;

    @Column(name = "google_account_id")
    private int googleAccoutId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
