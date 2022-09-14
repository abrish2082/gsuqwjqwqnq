/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Ifrs;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.Ifrs.FinancialInstrumentRegister;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.mapper.Ifrs.FinancialInstrumentRegisterFacade;

/**
 *
 * @author mz
 */
@Stateless
public class FinancialInstrumentRegisterBean implements FinancialInstrumentRegisterBeanLocal {

    @EJB
    FinancialInstrumentRegisterFacade finInstRegFacade;

    @Override
    public void create(FinancialInstrumentRegister finInstrumentRegister) {
        finInstRegFacade.create(finInstrumentRegister);
    }

    @Override
    public void edit(FinancialInstrumentRegister finInstrumentRegister) {
        finInstRegFacade.edit(finInstrumentRegister);
    }

    @Override
    public List<FinancialInstrumentRegister> searchAll() {
        return finInstRegFacade.findAll();
    }

    @Override
    public List<FmsSubsidiaryLedger> findBySubLedger(String subsidiaryLedger) {
        return finInstRegFacade.findbysubLedger(subsidiaryLedger);
    }

    @Override
    public List<ColumnNameResolver> findColumns() {
        return finInstRegFacade.findColumns();
    }

    @Override
    public List<FinancialInstrumentRegister> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, String columnValue) {
        return finInstRegFacade.searchByCol_NameAndCol_Value(columnNameResolver, columnValue);
    }

}
