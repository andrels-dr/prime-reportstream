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

import java.util.UUID;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Result;


/**
 * Convenience access to all tables in public
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>public.action</code>.
     */
    public static final Action ACTION = Action.ACTION;

    /**
     * The table <code>public.find_sent_reports</code>.
     */
    public static final FindSentReports FIND_SENT_REPORTS = FindSentReports.FIND_SENT_REPORTS;

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
    public static final FindWitheredReports FIND_WITHERED_REPORTS = FindWitheredReports.FIND_WITHERED_REPORTS;

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
    public static final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    /**
     * The table <code>public.item_descendants</code>.
     */
    public static final ItemDescendants ITEM_DESCENDANTS = ItemDescendants.ITEM_DESCENDANTS;

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
    public static final ItemLineage ITEM_LINEAGE = ItemLineage.ITEM_LINEAGE;

    /**
     * The table <code>public.report_ancestors</code>.
     */
    public static final ReportAncestors REPORT_ANCESTORS = ReportAncestors.REPORT_ANCESTORS;

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
    public static final ReportDescendants REPORT_DESCENDANTS = ReportDescendants.REPORT_DESCENDANTS;

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
    public static final ReportFile REPORT_FILE = ReportFile.REPORT_FILE;

    /**
     * The table <code>public.report_lineage</code>.
     */
    public static final ReportLineage REPORT_LINEAGE = ReportLineage.REPORT_LINEAGE;

    /**
     * The table <code>public.report_ur_ancestors</code>.
     */
    public static final ReportUrAncestors REPORT_UR_ANCESTORS = ReportUrAncestors.REPORT_UR_ANCESTORS;

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
    public static final Setting SETTING = Setting.SETTING;

    /**
     * The table <code>public.task</code>.
     */
    public static final Task TASK = Task.TASK;
}