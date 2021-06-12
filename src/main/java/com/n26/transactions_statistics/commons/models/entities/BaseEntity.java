package com.n26.transactions_statistics.commons.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.n26.transactions_statistics.utils.DateUtil;
import com.n26.transactions_statistics.utils.TimeUtility;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * @author mir00r on 11/6/21
 * @project IntelliJ IDEA
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "uuid_str", nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false)
    private boolean deleted;

    @PrePersist
    private void onBasePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = createdAt;
        this.createdBy = this.getLoggedInUsername();
        this.uuid = UUID.randomUUID().toString();
    }

    @PreUpdate
    private void onBaseUpdate() {
        this.updatedAt = Instant.now();
        this.updatedBy = this.getLoggedInUsername();
    }

    public boolean isNew() {
        return this.id == null;
    }

    @JsonIgnore
    public String getLoggedInUsername() {
        return "admin";
    }

    @JsonIgnore
    public String getCreatedAtReadable() {
        return TimeUtility.readableDateTimeFromInstant(this.createdAt);
    }

    @JsonIgnore
    public String getUpdatedAtReadable() {
        return TimeUtility.readableDateTimeFromInstant(this.updatedAt);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getReadableDate(Date date) {
        return DateUtil.getReadableDate(date);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Instant getCreatedAt() {
        return createdAt;
    }


    public Instant getUpdatedAt() {
        return updatedAt;
    }


    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
