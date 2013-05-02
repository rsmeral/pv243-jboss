package cz.muni.fi.pv243.et.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Money transaction. Used in be cz.muni.fi.pv243.et.model.MoneyTransfer or cz.muni.fi.pv243.et.model.Payment
 */
public class Transaction {

    private Long id;
    private Date date;
    private BigDecimal value;
    private TransactionType clazz;
    private Long ttId;

}
