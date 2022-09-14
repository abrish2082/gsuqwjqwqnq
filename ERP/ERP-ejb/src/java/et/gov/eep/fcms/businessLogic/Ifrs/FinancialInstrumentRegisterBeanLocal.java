/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Ifrs;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.Ifrs.FinancialInstrumentRegister;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;

/**
 *
 * @author mz
 */
@Local
public interface FinancialInstrumentRegisterBeanLocal {

    public void create(FinancialInstrumentRegister finInstrumentRegister);

    public void edit(FinancialInstrumentRegister finInstrumentRegister);

    public List<FinancialInstrumentRegister> searchAll();

    public List<FmsSubsidiaryLedger> findBySubLedger(String subsidiaryLedger);

    public List<ColumnNameResolver> findColumns();

    public List<FinancialInstrumentRegister> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, String columnValue);

}
