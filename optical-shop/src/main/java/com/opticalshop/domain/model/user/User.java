package com.opticalshop.domain.model.user;

import com.opticalshop.domain.exception.DomainException;
import java.time.LocalDateTime;
import java.util.UUID;

public class User {
    private final UUID id;
    private String username;
    private String email;
    private String passwordHash;
    private String fullName;
    private String phone;
    private String address;
    private boolean active;
    private final LocalDateTime createdAt;

    public User(UUID id, String username, String email, String passwordHash,
                String fullName, String phone, String address) {
        if (email == null || email.isBlank()) throw new DomainException("Email cannot be blank");
        if (username == null || username.isBlank()) throw new DomainException("Username cannot be blank");
        this.id = id; this.username = username; this.email = email;
        this.passwordHash = passwordHash; this.fullName = fullName;
        this.phone = phone; this.address = address;
        this.active = true; this.createdAt = LocalDateTime.now();
    }

    public static User create(String username, String email, String passwordHash,
                              String fullName, String phone, String address) {
        return new User(UUID.randomUUID(), username, email, passwordHash, fullName, phone, address);
    }

    public void deactivate() { this.active = false; }
    public void activate() { this.active = true; }
    public void updateProfile(String fullName, String phone, String address) {
        this.fullName = fullName; this.phone = phone; this.address = address;
    }

    public UUID getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public boolean isActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
