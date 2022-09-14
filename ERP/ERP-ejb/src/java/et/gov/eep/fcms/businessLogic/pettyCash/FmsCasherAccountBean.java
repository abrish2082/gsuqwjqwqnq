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
import et.gov.eep.fcms.mapper.pettyCash.FmsCasherAccountFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;

/**
 *
 * @author AB
 */
@Stateless
public class FmsCasherAccountBean implements FmsCasherAccountBeanLocal {

    @EJB
    FmsCasherAccountFacade casherAccountFacade;

    @Override
    public void create(FmsCasherAccount casherAccountEnty) {
        casherAccountFacade.create(casherAccountEnty);
    }

    @Override
    public List<FmsCasherAccount> findAll() {
        return casherAccountFacade.findAll();
    }

    @Override
    public void edit(FmsCasherAccount casherAccountEnty) {
        casherAccountFacade.edit(casherAccountEnty);
    }

    @Override
    public List<FmsCasherAccount> searchCasherName(FmsCasherAccount casherAccountEnty) {
        return casherAccountFacade.searchCasherName(casherAccountEnty);
    }

    @Override
    public FmsCasherAccount getCasherAccountInfo(FmsCasherAccount casherAccountEnty) {
        return casherAccountFacade.getCasherAccountInfo(casherAccountEnty);
    }

    @Override
    public List<FmsCasherAccount> getCasherAccountAllInfo() {
        return casherAccountFacade.findAll();
    }

    @Override
    public List<FmsCasherAccount> findAllCasher() {
        return casherAccountFacade.findAll();
    }

    @Override
    public FmsCasherAccount searchCash(FmsCasherAccount casherAccountEnty) {
        return casherAccountFacade.searchCasher(casherAccountEnty);//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FmsCasherAccount> findAllCash() {
        return casherAccountFacade.findAllCash();
    }

    @Override
    public List<FmsCasherAccount> getAllCashierName() {
        return casherAccountFacade.findAll();
    }

    @Override
    public FmsCasherAccount getSlcode(FmsCasherAccount casherAccountEnty) {
        return casherAccountFacade.findSlCode(casherAccountEnty);
    }

    @Override
    public List<FmsCasherAccount> searchCasherByParameter(HrEmployees empEnty) {
        return casherAccountFacade.searchCasherByParameter(empEnty);
    }

    @Override
    public FmsCasherAccount getCashierAccByEmpId(HrEmployees empEnty) {
        return casherAccountFacade.getCashierAccByEmpId(empEnty);
    }

    @Override
    public List<FmsCasherAccount> searchAllCasher() {
        return casherAccountFacade.findAll();
    }

    @Override
    public FmsCasherAccount getById(FmsCasherAccount casherAccountEnty) {
        return casherAccountFacade.getById(casherAccountEnty);
    }

    @Override
    public boolean findDup(HrEmployees empEnty) {
        return casherAccountFacade.findDup(empEnty);
    }
}
