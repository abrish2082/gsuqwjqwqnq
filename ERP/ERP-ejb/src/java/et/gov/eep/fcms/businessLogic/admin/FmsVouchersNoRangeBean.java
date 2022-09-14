/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.admin;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsLuVouchersType;
import et.gov.eep.fcms.entity.admin.FmsVouchersNoRange;
import et.gov.eep.fcms.mapper.admin.FmsAccountingPeriodFacade;
import et.gov.eep.fcms.mapper.admin.FmsLuVouchersTypeFacade;
import et.gov.eep.fcms.mapper.admin.FmsVouchersNoRangeFacade;

/**
 *
 * @author userPCAdmin
 */
@Stateless
public class FmsVouchersNoRangeBean implements FmsVouchersNoRangeBeanLocal {
//<editor-fold defaultstate="collapsed" desc="@EJB">

    @EJB
    FmsVouchersNoRangeFacade vouchersNoRangeFacade;
    @EJB
    FmsLuVouchersTypeFacade luVouchersTypeFacade;
    @EJB
    FmsAccountingPeriodFacade accountingPeriodFacade;
//</editor-fold>

    /**
     *
     * @param vouchersNoRange
     * @return
     */
    public FmsVouchersNoRange getCurrentVoucherNumber(FmsVouchersNoRange vouchersNoRange) {
        return vouchersNoRangeFacade.getCurrentVoucherNumber(vouchersNoRange);

    }

    /**
     *
     * @param vouchersNoRange
     */
    @Override
    public void edit(FmsVouchersNoRange vouchersNoRange) {
        vouchersNoRangeFacade.edit(vouchersNoRange);
    }

    /**
     *
     * @return
     */
    @Override
    public List<FmsLuVouchersType> getLuVouchersType() {
        return luVouchersTypeFacade.findAll();

    }

    /**
     *
     * @return
     */
    @Override
    public FmsAccountingPeriod getFiscalYr() {
        return accountingPeriodFacade.getCurretActivePeriod();

    }

    /**
     *
     * @param vouchersNoRangeSearch
     * @return
     */
    @Override
    public List<FmsVouchersNoRange> searchByVoucherType(FmsVouchersNoRange vouchersNoRangeSearch) {
        return vouchersNoRangeFacade.searchByVoucherType(vouchersNoRangeSearch);
    }

    /**
     *
     * @param vouchersNoRange
     */
    @Override
    public void create(FmsVouchersNoRange vouchersNoRange) {
        vouchersNoRangeFacade.updateStatus(vouchersNoRange);
        vouchersNoRangeFacade.create(vouchersNoRange);
    }

    /**
     *
     * @param vouchersType
     * @return
     */
    @Override
    public FmsLuVouchersType searchPaymentType(FmsLuVouchersType vouchersType) {
        return luVouchersTypeFacade.searchPaymentType(vouchersType);
    }

    @Override
    public FmsVouchersNoRange getCurrentVoucherById(FmsVouchersNoRange fmsVouchersNoRange) {
        return vouchersNoRangeFacade.getCurrentVoucherById(fmsVouchersNoRange);
    }
}
