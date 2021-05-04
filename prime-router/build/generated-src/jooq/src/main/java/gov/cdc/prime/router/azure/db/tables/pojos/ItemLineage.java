/*
 * This file is generated by jOOQ.
 */
package gov.cdc.prime.router.azure.db.tables.pojos;


import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ItemLineage implements Serializable {

    private static final long serialVersionUID = -2119395013;

    private Long           itemLineageId;
    private UUID           parentReportId;
    private Integer        parentIndex;
    private UUID           childReportId;
    private Integer        childIndex;
    private String         trackingId;
    private String         transportResult;
    private OffsetDateTime createdAt;

    public ItemLineage() {}

    public ItemLineage(ItemLineage value) {
        this.itemLineageId = value.itemLineageId;
        this.parentReportId = value.parentReportId;
        this.parentIndex = value.parentIndex;
        this.childReportId = value.childReportId;
        this.childIndex = value.childIndex;
        this.trackingId = value.trackingId;
        this.transportResult = value.transportResult;
        this.createdAt = value.createdAt;
    }

    public ItemLineage(
        Long           itemLineageId,
        UUID           parentReportId,
        Integer        parentIndex,
        UUID           childReportId,
        Integer        childIndex,
        String         trackingId,
        String         transportResult,
        OffsetDateTime createdAt
    ) {
        this.itemLineageId = itemLineageId;
        this.parentReportId = parentReportId;
        this.parentIndex = parentIndex;
        this.childReportId = childReportId;
        this.childIndex = childIndex;
        this.trackingId = trackingId;
        this.transportResult = transportResult;
        this.createdAt = createdAt;
    }

    public Long getItemLineageId() {
        return this.itemLineageId;
    }

    public ItemLineage setItemLineageId(Long itemLineageId) {
        this.itemLineageId = itemLineageId;
        return this;
    }

    public UUID getParentReportId() {
        return this.parentReportId;
    }

    public ItemLineage setParentReportId(UUID parentReportId) {
        this.parentReportId = parentReportId;
        return this;
    }

    public Integer getParentIndex() {
        return this.parentIndex;
    }

    public ItemLineage setParentIndex(Integer parentIndex) {
        this.parentIndex = parentIndex;
        return this;
    }

    public UUID getChildReportId() {
        return this.childReportId;
    }

    public ItemLineage setChildReportId(UUID childReportId) {
        this.childReportId = childReportId;
        return this;
    }

    public Integer getChildIndex() {
        return this.childIndex;
    }

    public ItemLineage setChildIndex(Integer childIndex) {
        this.childIndex = childIndex;
        return this;
    }

    public String getTrackingId() {
        return this.trackingId;
    }

    public ItemLineage setTrackingId(String trackingId) {
        this.trackingId = trackingId;
        return this;
    }

    public String getTransportResult() {
        return this.transportResult;
    }

    public ItemLineage setTransportResult(String transportResult) {
        this.transportResult = transportResult;
        return this;
    }

    public OffsetDateTime getCreatedAt() {
        return this.createdAt;
    }

    public ItemLineage setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ItemLineage other = (ItemLineage) obj;
        if (itemLineageId == null) {
            if (other.itemLineageId != null)
                return false;
        }
        else if (!itemLineageId.equals(other.itemLineageId))
            return false;
        if (parentReportId == null) {
            if (other.parentReportId != null)
                return false;
        }
        else if (!parentReportId.equals(other.parentReportId))
            return false;
        if (parentIndex == null) {
            if (other.parentIndex != null)
                return false;
        }
        else if (!parentIndex.equals(other.parentIndex))
            return false;
        if (childReportId == null) {
            if (other.childReportId != null)
                return false;
        }
        else if (!childReportId.equals(other.childReportId))
            return false;
        if (childIndex == null) {
            if (other.childIndex != null)
                return false;
        }
        else if (!childIndex.equals(other.childIndex))
            return false;
        if (trackingId == null) {
            if (other.trackingId != null)
                return false;
        }
        else if (!trackingId.equals(other.trackingId))
            return false;
        if (transportResult == null) {
            if (other.transportResult != null)
                return false;
        }
        else if (!transportResult.equals(other.transportResult))
            return false;
        if (createdAt == null) {
            if (other.createdAt != null)
                return false;
        }
        else if (!createdAt.equals(other.createdAt))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.itemLineageId == null) ? 0 : this.itemLineageId.hashCode());
        result = prime * result + ((this.parentReportId == null) ? 0 : this.parentReportId.hashCode());
        result = prime * result + ((this.parentIndex == null) ? 0 : this.parentIndex.hashCode());
        result = prime * result + ((this.childReportId == null) ? 0 : this.childReportId.hashCode());
        result = prime * result + ((this.childIndex == null) ? 0 : this.childIndex.hashCode());
        result = prime * result + ((this.trackingId == null) ? 0 : this.trackingId.hashCode());
        result = prime * result + ((this.transportResult == null) ? 0 : this.transportResult.hashCode());
        result = prime * result + ((this.createdAt == null) ? 0 : this.createdAt.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ItemLineage (");

        sb.append(itemLineageId);
        sb.append(", ").append(parentReportId);
        sb.append(", ").append(parentIndex);
        sb.append(", ").append(childReportId);
        sb.append(", ").append(childIndex);
        sb.append(", ").append(trackingId);
        sb.append(", ").append(transportResult);
        sb.append(", ").append(createdAt);

        sb.append(")");
        return sb.toString();
    }
}