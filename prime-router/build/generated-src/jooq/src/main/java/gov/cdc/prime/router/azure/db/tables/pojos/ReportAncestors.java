/*
 * This file is generated by jOOQ.
 */
package gov.cdc.prime.router.azure.db.tables.pojos;


import java.io.Serializable;
import java.util.UUID;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ReportAncestors implements Serializable {

    private static final long serialVersionUID = -571143155;

    private UUID reportAncestors;

    public ReportAncestors() {}

    public ReportAncestors(ReportAncestors value) {
        this.reportAncestors = value.reportAncestors;
    }

    public ReportAncestors(
        UUID reportAncestors
    ) {
        this.reportAncestors = reportAncestors;
    }

    public UUID getReportAncestors() {
        return this.reportAncestors;
    }

    public ReportAncestors setReportAncestors(UUID reportAncestors) {
        this.reportAncestors = reportAncestors;
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
        final ReportAncestors other = (ReportAncestors) obj;
        if (reportAncestors == null) {
            if (other.reportAncestors != null)
                return false;
        }
        else if (!reportAncestors.equals(other.reportAncestors))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.reportAncestors == null) ? 0 : this.reportAncestors.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ReportAncestors (");

        sb.append(reportAncestors);

        sb.append(")");
        return sb.toString();
    }
}