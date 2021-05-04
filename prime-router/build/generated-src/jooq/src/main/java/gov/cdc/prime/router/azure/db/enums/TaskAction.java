/*
 * This file is generated by jOOQ.
 */
package gov.cdc.prime.router.azure.db.enums;


import gov.cdc.prime.router.azure.db.Public;

import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public enum TaskAction implements EnumType {

    receive("receive"),

    translate("translate"),

    batch("batch"),

    send("send"),

    download("download"),

    wipe("wipe"),

    batch_error("batch_error"),

    send_error("send_error"),

    wipe_error("wipe_error"),

    none("none"),

    resend("resend"),

    rebatch("rebatch");

    private final String literal;

    private TaskAction(String literal) {
        this.literal = literal;
    }

    @Override
    public Catalog getCatalog() {
        return getSchema() == null ? null : getSchema().getCatalog();
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public String getName() {
        return "task_action";
    }

    @Override
    public String getLiteral() {
        return literal;
    }
}