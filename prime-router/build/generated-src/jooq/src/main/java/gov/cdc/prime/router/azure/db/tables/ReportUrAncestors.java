/*
 * This file is generated by jOOQ.
 */
package gov.cdc.prime.router.azure.db.tables;


import gov.cdc.prime.router.azure.db.Public;
import gov.cdc.prime.router.azure.db.tables.records.ReportUrAncestorsRecord;

import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row1;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ReportUrAncestors extends TableImpl<ReportUrAncestorsRecord> {

    private static final long serialVersionUID = 1111057562;

    /**
     * The reference instance of <code>public.report_ur_ancestors</code>
     */
    public static final ReportUrAncestors REPORT_UR_ANCESTORS = new ReportUrAncestors();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ReportUrAncestorsRecord> getRecordType() {
        return ReportUrAncestorsRecord.class;
    }

    /**
     * The column <code>public.report_ur_ancestors.report_ur_ancestors</code>.
     */
    public final TableField<ReportUrAncestorsRecord, UUID> REPORT_UR_ANCESTORS_ = createField(DSL.name("report_ur_ancestors"), org.jooq.impl.SQLDataType.UUID, this, "");

    /**
     * Create a <code>public.report_ur_ancestors</code> table reference
     */
    public ReportUrAncestors() {
        this(DSL.name("report_ur_ancestors"), null);
    }

    /**
     * Create an aliased <code>public.report_ur_ancestors</code> table reference
     */
    public ReportUrAncestors(String alias) {
        this(DSL.name(alias), REPORT_UR_ANCESTORS);
    }

    /**
     * Create an aliased <code>public.report_ur_ancestors</code> table reference
     */
    public ReportUrAncestors(Name alias) {
        this(alias, REPORT_UR_ANCESTORS);
    }

    private ReportUrAncestors(Name alias, Table<ReportUrAncestorsRecord> aliased) {
        this(alias, aliased, new Field[1]);
    }

    private ReportUrAncestors(Name alias, Table<ReportUrAncestorsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.function());
    }

    public <O extends Record> ReportUrAncestors(Table<O> child, ForeignKey<O, ReportUrAncestorsRecord> key) {
        super(child, key, REPORT_UR_ANCESTORS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public ReportUrAncestors as(String alias) {
        return new ReportUrAncestors(DSL.name(alias), this, parameters);
    }

    @Override
    public ReportUrAncestors as(Name alias) {
        return new ReportUrAncestors(alias, this, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public ReportUrAncestors rename(String name) {
        return new ReportUrAncestors(DSL.name(name), null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public ReportUrAncestors rename(Name name) {
        return new ReportUrAncestors(name, null, parameters);
    }

    // -------------------------------------------------------------------------
    // Row1 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row1<UUID> fieldsRow() {
        return (Row1) super.fieldsRow();
    }

    /**
     * Call this table-valued function
     */
    public ReportUrAncestors call(UUID startReportId) {
        return new ReportUrAncestors(DSL.name(getName()), null, new Field[] { 
              DSL.val(startReportId, org.jooq.impl.SQLDataType.UUID)
        });
    }

    /**
     * Call this table-valued function
     */
    public ReportUrAncestors call(Field<UUID> startReportId) {
        return new ReportUrAncestors(DSL.name(getName()), null, new Field[] { 
              startReportId
        });
    }
}