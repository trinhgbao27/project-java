package com.opticalshop.domain.model.prescription;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Đơn kính của khách hàng.
 * OD = mắt phải (Oculus Dexter), OS = mắt trái (Oculus Sinister)
 */
public class Prescription {
    private final UUID id;
    private final UUID customerId;
    private BigDecimal odSphere, odCylinder;
    private int odAxis;
    private BigDecimal osSphere, osCylinder;
    private int osAxis;
    private BigDecimal pd;
    private LocalDate issuedDate;
    private String issuedBy;
    private String notes;

    public Prescription(UUID id, UUID customerId,
                        BigDecimal odSphere, BigDecimal odCylinder, int odAxis,
                        BigDecimal osSphere, BigDecimal osCylinder, int osAxis,
                        BigDecimal pd, LocalDate issuedDate, String issuedBy, String notes) {
        this.id = id; this.customerId = customerId;
        this.odSphere = odSphere; this.odCylinder = odCylinder; this.odAxis = odAxis;
        this.osSphere = osSphere; this.osCylinder = osCylinder; this.osAxis = osAxis;
        this.pd = pd; this.issuedDate = issuedDate; this.issuedBy = issuedBy; this.notes = notes;
    }

    public static Prescription create(UUID customerId,
                                       BigDecimal odSphere, BigDecimal odCylinder, int odAxis,
                                       BigDecimal osSphere, BigDecimal osCylinder, int osAxis,
                                       BigDecimal pd, LocalDate issuedDate, String issuedBy, String notes) {
        return new Prescription(UUID.randomUUID(), customerId,
                odSphere, odCylinder, odAxis, osSphere, osCylinder, osAxis,
                pd, issuedDate, issuedBy, notes);
    }

    public UUID getId() { return id; }
    public UUID getCustomerId() { return customerId; }
    public BigDecimal getOdSphere() { return odSphere; }
    public BigDecimal getOdCylinder() { return odCylinder; }
    public int getOdAxis() { return odAxis; }
    public BigDecimal getOsSphere() { return osSphere; }
    public BigDecimal getOsCylinder() { return osCylinder; }
    public int getOsAxis() { return osAxis; }
    public BigDecimal getPd() { return pd; }
    public LocalDate getIssuedDate() { return issuedDate; }
    public String getIssuedBy() { return issuedBy; }
    public String getNotes() { return notes; }
}
