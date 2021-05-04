/*
 * This file is generated by jOOQ.
 */
package gov.cdc.prime.router.azure.db;


import gov.cdc.prime.router.azure.db.tables.FlywaySchemaHistory;
import gov.cdc.prime.router.azure.db.tables.ItemLineage;
import gov.cdc.prime.router.azure.db.tables.ReportFile;
import gov.cdc.prime.router.azure.db.tables.Setting;
import gov.cdc.prime.router.azure.db.tables.Task;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>public</code> schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index FLYWAY_SCHEMA_HISTORY_S_IDX = Indexes0.FLYWAY_SCHEMA_HISTORY_S_IDX;
    public static final Index ITEM_LINEAGE_CHILD_IDX = Indexes0.ITEM_LINEAGE_CHILD_IDX;
    public static final Index ITEM_LINEAGE_PARENT_IDX = Indexes0.ITEM_LINEAGE_PARENT_IDX;
    public static final Index ITEM_LINEAGE_TRACKING_ID_IDX = Indexes0.ITEM_LINEAGE_TRACKING_ID_IDX;
    public static final Index REPORT_FILE_NEXT_ACTION_IDX = Indexes0.REPORT_FILE_NEXT_ACTION_IDX;
    public static final Index SETTING_ORGANIZATION_ID_IDX = Indexes0.SETTING_ORGANIZATION_ID_IDX;
    public static final Index SETTING_TYPE_ORGANIZATION_ID_NAME_IDX = Indexes0.SETTING_TYPE_ORGANIZATION_ID_NAME_IDX;
    public static final Index TASK_NEXT_ACTION_IDX = Indexes0.TASK_NEXT_ACTION_IDX;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index FLYWAY_SCHEMA_HISTORY_S_IDX = Internal.createIndex("flyway_schema_history_s_idx", FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, new OrderField[] { FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.SUCCESS }, false);
        public static Index ITEM_LINEAGE_CHILD_IDX = Internal.createIndex("item_lineage_child_idx", ItemLineage.ITEM_LINEAGE, new OrderField[] { ItemLineage.ITEM_LINEAGE.CHILD_REPORT_ID, ItemLineage.ITEM_LINEAGE.CHILD_INDEX }, false);
        public static Index ITEM_LINEAGE_PARENT_IDX = Internal.createIndex("item_lineage_parent_idx", ItemLineage.ITEM_LINEAGE, new OrderField[] { ItemLineage.ITEM_LINEAGE.PARENT_REPORT_ID, ItemLineage.ITEM_LINEAGE.PARENT_INDEX }, false);
        public static Index ITEM_LINEAGE_TRACKING_ID_IDX = Internal.createIndex("item_lineage_tracking_id_idx", ItemLineage.ITEM_LINEAGE, new OrderField[] { ItemLineage.ITEM_LINEAGE.TRACKING_ID }, false);
        public static Index REPORT_FILE_NEXT_ACTION_IDX = Internal.createIndex("report_file_next_action_idx", ReportFile.REPORT_FILE, new OrderField[] { ReportFile.REPORT_FILE.NEXT_ACTION }, false);
        public static Index SETTING_ORGANIZATION_ID_IDX = Internal.createIndex("setting_organization_id_idx", Setting.SETTING, new OrderField[] { Setting.SETTING.ORGANIZATION_ID }, false);
        public static Index SETTING_TYPE_ORGANIZATION_ID_NAME_IDX = Internal.createIndex("setting_type_organization_id_name_idx", Setting.SETTING, new OrderField[] { Setting.SETTING.TYPE, Setting.SETTING.ORGANIZATION_ID, Setting.SETTING.NAME }, true);
        public static Index TASK_NEXT_ACTION_IDX = Internal.createIndex("task_next_action_idx", Task.TASK, new OrderField[] { Task.TASK.NEXT_ACTION }, false);
    }
}