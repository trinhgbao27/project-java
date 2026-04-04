package com.opticalshop.adapter.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "prescriptions")
public class PrescriptionJpaEntity {

    @Id
    @Column(columnDefinition = "uniqueidentifier")
    private UUID id;

    @Column(name = "customer_id", nullable = false, columnDefinition = "uniqueidentifier")
    private UUID customerId;

    @Column(name = "od_sphere", precision = 5, scale = 2)
    private BigDecimal odSphere;

    @Column(name = "od_cylinder", precision = 5, scale = 2)
    private BigDecimal odCylinder;

    @Column(name = "od_axis")
    private int odAxis;

    @Column(name = "os_sphere", precision = 5, scale = 2)
    private BigDecimal osSphere;

    @Column(name = "os_cylinder", precision = 5, scale = 2)
    private BigDecimal osCylinder;

    @Column(name = "os_axis")
    private int osAxis;

    @Column(precision = 5, scale = 2)
    private BigDecimal pd;

    @Column(name = "issued_date")
    private LocalDate issuedDate;

    @Column(name = "issued_by")
    private String issuedBy;

    private String notes;

    public PrescriptionJpaEntity() {}

    // Getters & Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }
    public BigDecimal getOdSphere() { return odSphere; }
    public void setOdSphere(BigDecimal odSphere) { this.odSphere = odSphere; }
    public BigDecimal getOdCylinder() { return odCylinder; }
    public void setOdCylinder(BigDecimal odCylinder) { this.odCylinder = odCylinder; }
    public int getOdAxis() { return odAxis; }
    public void setOdAxis(int odAxis) { this.odAxis = odAxis; }
    public BigDecimal getOsSphere() { return osSphere; }
    public void setOsSphere(BigDecimal osSphere) { this.osSphere = osSphere; }
    public BigDecimal getOsCylinder() { return osCylinder; }
    public void setOsCylinder(BigDecimal osCylinder) { this.osCylinder = osCylinder; }
    public int getOsAxis() { return osAxis; }
    public void setOsAxis(int osAxis) { this.osAxis = osAxis; }
    public BigDecimal getPd() { return pd; }
    public void setPd(BigDecimal pd) { this.pd = pd; }
    public LocalDate getIssuedDate() { return issuedDate; }
    public void setIssuedDate(LocalDate issuedDate) { this.issuedDate = issuedDate; }
    public String getIssuedBy() { return issuedBy; }
    public void setIssuedBy(String issuedBy) { this.issuedBy = issuedBy; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
