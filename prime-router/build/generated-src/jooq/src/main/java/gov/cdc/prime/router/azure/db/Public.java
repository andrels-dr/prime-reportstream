/*
 * This file is generated by jOOQ.
 */
package gov.cdc.prime.router.azure.db;


import gov.cdc.prime.router.azure.db.tables.Action;
import gov.cdc.prime.router.azure.db.tables.FindSentReports;
import gov.cdc.prime.router.azure.db.tables.FindWitheredReports;
import gov.cdc.prime.router.azure.db.tables.FlywaySchemaHistory;
import gov.cdc.prime.router.azure.db.tables.ItemDescendants;
import gov.cdc.prime.router.azure.db.tables.ItemLineage;
import gov.cdc.prime.router.azure.db.tables.ReportAncestors;
import gov.cdc.prime.router.azure.db.tables.ReportDescendants;
import gov.cdc.prime.router.azure.db.tables.ReportFile;
import gov.cdc.prime.router.azure.db.tables.ReportLineage;
import gov.cdc.prime.router.azure.db.tables.ReportUrAncestors;
import gov.cdc.prime.router.azure.db.tables.Setting;
import gov.cdc.prime.router.azure.db.tables.Task;
import gov.cdc.prime.router.azure.db.tables.records.FindSentReportsRecord;
import gov.cdc.prime.router.azure.db.tables.records.FindWitheredReportsRecord;
import gov.cdc.prime.router.azure.db.tables.records.ItemDescendantsRecord;
import gov.cdc.prime.router.azure.db.tables.records.ReportAncestorsRecord;
import gov.cdc.prime.router.azure.db.tables.records.ReportDescendantsRecord;
import gov.cdc.prime.router.azure.db.tables.records.ReportUrAncestorsRecord;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Catalog;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Result;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = -194644208;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.action</code>.
     */
    public final Action ACTION = Action.ACTION;

    /**
     * The table <code>public.find_sent_reports</code>.
     */
    public final FindSentReports FIND_SENT_REPORTS = FindSentReports.FIND_SENT_REPORTS;

    /**
     * Call <code>public.find_sent_reports</code>.
     */
    public static Result<FindSentReportsRecord> FIND_SENT_REPORTS(Configuration configuration, UUID startReportId) {
        return configuration.dsl().selectFrom(gov.cdc.prime.router.azure.db.tables.FindSentReports.FIND_SENT_REPORTS.call(startReportId)).fetch();
    }

    /**
     * Get <code>public.find_sent_reports</code> as a table.
     */
    public static FindSentReports FIND_SENT_REPORTS(UUID startReportId) {
        return gov.cdc.prime.router.azure.db.tables.FindSentReports.FIND_SENT_REPORTS.call(startReportId);
    }

    /**
     * Get <code>public.find_sent_reports</code> as a table.
     */
    public static FindSentReports FIND_SENT_REPORTS(Field<UUID> startReportId) {
        return gov.cdc.prime.router.azure.db.tables.FindSentReports.FIND_SENT_REPORTS.call(startReportId);
    }

    /**
     * The table <code>public.find_withered_reports</code>.
     */
    public final FindWitheredReports FIND_WITHERED_REPORTS = FindWitheredReports.FIND_WITHERED_REPORTS;

    /**
     * Call <code>public.find_withered_reports</code>.
     */
    public static Result<FindWitheredReportsRecord> FIND_WITHERED_REPORTS(Configuration configuration, UUID startReportId) {
        return configuration.dsl().selectFrom(gov.cdc.prime.router.azure.db.tables.FindWitheredReports.FIND_WITHERED_REPORTS.call(startReportId)).fetch();
    }

    /**
     * Get <code>public.find_withered_reports</code> as a table.
     */
    public static FindWitheredReports FIND_WITHERED_REPORTS(UUID startReportId) {
        return gov.cdc.prime.router.azure.db.tables.FindWitheredReports.FIND_WITHERED_REPORTS.call(startReportId);
    }

    /**
     * Get <code>public.find_withered_reports</code> as a table.
     */
    public static FindWitheredReports FIND_WITHERED_REPORTS(Field<UUID> startReportId) {
        return gov.cdc.prime.router.azure.db.tables.FindWitheredReports.FIND_WITHERED_REPORTS.call(startReportId);
    }

    /**
     * The table <code>public.flyway_schema_history</code>.
     */
    public final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    /**
     * The table <code>public.item_descendants</code>.
     */
    public final ItemDescendants ITEM_DESCENDANTS = ItemDescendants.ITEM_DESCENDANTS;

    /**
     * Call <code>public.item_descendants</code>.
     */
    public static Result<ItemDescendantsRecord> ITEM_DESCENDANTS(Configuration configuration, UUID startReportId) {
        return configuration.dsl().selectFrom(gov.cdc.prime.router.azure.db.tables.ItemDescendants.ITEM_DESCENDANTS.call(startReportId)).fetch();
    }

    /**
     * Get <code>public.item_descendants</code> as a table.
     */
    public static ItemDescendants ITEM_DESCENDANTS(UUID startReportId) {
        return gov.cdc.prime.router.azure.db.tables.ItemDescendants.ITEM_DESCENDANTS.call(startReportId);
    }

    /**
     * Get <code>public.item_descendants</code> as a table.
     */
    public static ItemDescendants ITEM_DESCENDANTS(Field<UUID> startReportId) {
        return gov.cdc.prime.router.azure.db.tables.ItemDescendants.ITEM_DESCENDANTS.call(startReportId);
    }

    /**
     * The table <code>public.item_lineage</code>.
     */
    public final ItemLineage ITEM_LINEAGE = ItemLineage.ITEM_LINEAGE;

    /**
     * The table <code>public.report_ancestors</code>.
     */
    public final ReportAncestors REPORT_ANCESTORS = ReportAncestors.REPORT_ANCESTORS;

    /**
     * Call <code>public.report_ancestors</code>.
     */
    public static Result<ReportAncestorsRecord> REPORT_ANCESTORS(Configuration configuration, UUID startReportId) {
        return configuration.dsl().selectFrom(gov.cdc.prime.router.azure.db.tables.ReportAncestors.REPORT_ANCESTORS.call(startReportId)).fetch();
    }

    /**
     * Get <code>public.report_ancestors</code> as a table.
     */
    public static ReportAncestors REPORT_ANCESTORS(UUID startReportId) {
        return gov.cdc.prime.router.azure.db.tables.ReportAncestors.REPORT_ANCESTORS.call(startReportId);
    }

    /**
     * Get <code>public.report_ancestors</code> as a table.
     */
    public static ReportAncestors REPORT_ANCESTORS(Field<UUID> startReportId) {
        return gov.cdc.prime.router.azure.db.tables.ReportAncestors.REPORT_ANCESTORS.call(startReportId);
    }

    /**
     * The table <code>public.report_descendants</code>.
     */
    public final ReportDescendants REPORT_DESCENDANTS = ReportDescendants.REPORT_DESCENDANTS;

    /**
     * Call <code>public.report_descendants</code>.
     */
    public static Result<ReportDescendantsRecord> REPORT_DESCENDANTS(Configuration configuration, UUID startReportId) {
        return configuration.dsl().selectFrom(gov.cdc.prime.router.azure.db.tables.ReportDescendants.REPORT_DESCENDANTS.call(startReportId)).fetch();
    }

    /**
     * Get <code>public.report_descendants</code> as a table.
     */
    public static ReportDescendants REPORT_DESCENDANTS(UUID startReportId) {
        return gov.cdc.prime.router.azure.db.tables.ReportDescendants.REPORT_DESCENDANTS.call(startReportId);
    }

    /**
     * Get <code>public.report_descendants</code> as a table.
     */
    public static ReportDescendants REPORT_DESCENDANTS(Field<UUID> startReportId) {
        return gov.cdc.prime.router.azure.db.tables.ReportDescendants.REPORT_DESCENDANTS.call(startReportId);
    }

    /**
     * The table <code>public.report_file</code>.
     */
    public final ReportFile REPORT_FILE = ReportFile.REPORT_FILE;

    /**
     * The table <code>public.report_lineage</code>.
     */
    public final ReportLineage REPORT_LINEAGE = ReportLineage.REPORT_LINEAGE;

    /**
     * The table <code>public.report_ur_ancestors</code>.
     */
    public final ReportUrAncestors REPORT_UR_ANCESTORS = ReportUrAncestors.REPORT_UR_ANCESTORS;

    /**
     * Call <code>public.report_ur_ancestors</code>.
     */
    public static Result<ReportUrAncestorsRecord> REPORT_UR_ANCESTORS(Configuration configuration, UUID startReportId) {
        return configuration.dsl().selectFrom(gov.cdc.prime.router.azure.db.tables.ReportUrAncestors.REPORT_UR_ANCESTORS.call(startReportId)).fetch();
    }

    /**
     * Get <code>public.report_ur_ancestors</code> as a table.
     */
    public static ReportUrAncestors REPORT_UR_ANCESTORS(UUID startReportId) {
        return gov.cdc.prime.router.azure.db.tables.ReportUrAncestors.REPORT_UR_ANCESTORS.call(startReportId);
    }

    /**
     * Get <code>public.report_ur_ancestors</code> as a table.
     */
    public static ReportUrAncestors REPORT_UR_ANCESTORS(Field<UUID> startReportId) {
        return gov.cdc.prime.router.azure.db.tables.ReportUrAncestors.REPORT_UR_ANCESTORS.call(startReportId);
    }

    /**
     * The table <code>public.setting</code>.
     */
    public final Setting SETTING = Setting.SETTING;

    /**
     * The table <code>public.task</code>.
     */
    public final Task TASK = Task.TASK;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        return Arrays.<Sequence<?>>asList(
            Sequences.ACTION_ACTION_ID_SEQ,
            Sequences.ITEM_LINEAGE_ITEM_LINEAGE_ID_SEQ,
            Sequences.REPORT_LINEAGE_REPORT_LINEAGE_ID_SEQ,
            Sequences.SETTING_SETTING_ID_SEQ);
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            Action.ACTION,
            FindSentReports.FIND_SENT_REPORTS,
            FindWitheredReports.FIND_WITHERED_REPORTS,
            FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
            ItemDescendants.ITEM_DESCENDANTS,
            ItemLineage.ITEM_LINEAGE,
            ReportAncestors.REPORT_ANCESTORS,
            ReportDescendants.REPORT_DESCENDANTS,
            ReportFile.REPORT_FILE,
            ReportLineage.REPORT_LINEAGE,
            ReportUrAncestors.REPORT_UR_ANCESTORS,
            Setting.SETTING,
            Task.TASK);
    }
}