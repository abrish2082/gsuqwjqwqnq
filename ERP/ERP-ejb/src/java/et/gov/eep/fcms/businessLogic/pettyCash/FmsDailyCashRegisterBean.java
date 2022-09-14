/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.pettyCash;


import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.pettyCash.FmsCasherAccount;
import et.gov.eep.fcms.entity.pettyCash.FmsDailyCashRegister;
import et.gov.eep.fcms.entity.pettyCash.FmsPettyCashReplenishment;
import et.gov.eep.fcms.mapper.pettyCash.FmsDailyCashRegisterFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
/**
 *
 * @author muller
 */
@Stateless
public class FmsDailyCashRegisterBean implements FmsDailyCashRegisterBeanLocal {

    @EJB
    private FmsDailyCashRegisterFacade cashRegisterFacade;

    @Override
    public List<FmsDailyCashRegister> searchAmountAndDateByCasherID(FmsDailyCashRegister cashRegister) {
        return cashRegisterFacade.searchAmount(cashRegister);

    }

    @Override
    public void create(FmsDailyCashRegister dailyCashRegisterEnty) {
        cashRegisterFacade.create(dailyCashRegisterEnty);
    }

    @Override
    public void edit(FmsDailyCashRegister dailyCashRegisterEnty) {
        cashRegisterFacade.edit(dailyCashRegisterEnty);
    }

    @Override
    public List<FmsDailyCashRegister> searchByDate(FmsDailyCashRegister dailyCashRegisterEnty) {
        return cashRegisterFacade.searchByDate(dailyCashRegisterEnty);
    }

    @Override
    public List<FmsDailyCashRegister> getdate(FmsDailyCashRegister dailyCashRegisterEnty) {
        return cashRegisterFacade.searchByDate(dailyCashRegisterEnty);
    }

    @Override
    public List<FmsDailyCashRegister> getAllList() {
        return cashRegisterFacade.findAll();
    }

    @Override
    public List<FmsDailyCashRegister> getListByName(HrEmployees empEnty) {
        return cashRegisterFacade.getListByName(empEnty);
    }

    @Override
    public FmsDailyCashRegister getById(FmsDailyCashRegister dailyCashRegisterEnty) {
        return cashRegisterFacade.getDataById(dailyCashRegisterEnty);
    }

    @Override
    public List<FmsDailyCashRegister> findDailyPaidListByChaserIdAndStatus(FmsCasherAccount casherAccountEnty) {
        return cashRegisterFacade.findDailyPaidListByChaserIdAndStatus(casherAccountEnty);
    }

    @Override
    public List<FmsDailyCashRegister> findByCashierIdAndStatus(FmsCasherAccount casherAccountEnty, int dailyCashRegStatus) {
        return cashRegisterFacade.findByCashierIdAndStatus(casherAccountEnty, dailyCashRegStatus);
    }

    @Override
    public List<FmsDailyCashRegister> findByCashierIdAndWfStatus(FmsCasherAccount casherAccountEnty, FmsPettyCashReplenishment fmsPettyCashReplenishment) {
        return cashRegisterFacade.findByCashierIdAndWfStatus(casherAccountEnty, fmsPettyCashReplenishment);
    }
}
