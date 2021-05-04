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
public class FindSentReports implements Serializable {

    private static final long serialVersionUID = -1120112247;

    private UUID findSentReports;

    public FindSentReports() {}

    public FindSentReports(FindSentReports value) {
        this.findSentReports = value.findSentReports;
    }

    public FindSentReports(
        UUID findSentReports
    ) {
        this.findSentReports = findSentReports;
    }

    public UUID getFindSentReports() {
        return this.findSentReports;
    }

    public FindSentReports setFindSentReports(UUID findSentReports) {
        this.findSentReports = findSentReports;
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
        final FindSentReports other = (FindSentReports) obj;
        if (findSentReports == null) {
            if (other.findSentReports != null)
                return false;
        }
        else if (!findSentReports.equals(other.findSentReports))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.findSentReports == null) ? 0 : this.findSentReports.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("FindSentReports (");

        sb.append(findSentReports);

        sb.append(")");
        return sb.toString();
    }
}