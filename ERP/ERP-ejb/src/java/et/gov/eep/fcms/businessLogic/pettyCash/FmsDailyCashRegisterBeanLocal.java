/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.pettyCash;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.pettyCash.FmsCasherAccount;
import et.gov.eep.fcms.entity.pettyCash.FmsDailyCashRegister;
import et.gov.eep.fcms.entity.pettyCash.FmsPettyCashReplenishment;
import et.gov.eep.hrms.entity.employee.HrEmployees;

/**
 *
 * @author muller
 */
@Local
public interface FmsDailyCashRegisterBeanLocal {

    List<FmsDailyCashRegister> searchAmountAndDateByCasherID(FmsDailyCashRegister cashRegister);

    public void create(FmsDailyCashRegister dailyCashRegister);

    public void edit(FmsDailyCashRegister dailyCashRegister);

    public FmsDailyCashRegister getById(FmsDailyCashRegister dailyCashRegisterEnty);

    public List<FmsDailyCashRegister> searchByDate(FmsDailyCashRegister dailyCashRegisterEnty);

    public List<FmsDailyCashRegister> getdate(FmsDailyCashRegister dailyCashRegisterEnty);

    public List<FmsDailyCashRegister> getAllList();

    public List<FmsDailyCashRegister> getListByName(HrEmployees empEnty);

    public List<FmsDailyCashRegister> findDailyPaidListByChaserIdAndStatus(FmsCasherAccount casherAccountEnty);

    public List<FmsDailyCashRegister> findByCashierIdAndStatus(FmsCasherAccount casherAccountEnty, int dailyCashRegStatus);

    public List<FmsDailyCashRegister> findByCashierIdAndWfStatus(FmsCasherAccount casherAccountEnty, FmsPettyCashReplenishment fmsPettyCashReplenishment);
}
