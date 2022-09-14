/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.pettyCash;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.pettyCash.FmsCasherAccount;
import et.gov.eep.hrms.entity.employee.HrEmployees;

/**
 *
 * @author AB
 */
@Local
public interface FmsCasherAccountBeanLocal {

    public List<FmsCasherAccount> findAll();

    public void create(FmsCasherAccount casherAccountEnty);

    public void edit(FmsCasherAccount casherAccountEnty);

    public List<FmsCasherAccount> searchCasherName(FmsCasherAccount casherAccountEnty);

    public List<FmsCasherAccount> getCasherAccountAllInfo();

    public List<FmsCasherAccount> findAllCasher();

    public List<FmsCasherAccount> getAllCashierName();

    public List<FmsCasherAccount> searchCasherByParameter(HrEmployees empEnty);

    public List<FmsCasherAccount> searchAllCasher();

    public List<FmsCasherAccount> findAllCash();

    public FmsCasherAccount searchCash(FmsCasherAccount casherAccountEnty);

    public FmsCasherAccount getCasherAccountInfo(FmsCasherAccount casherAccountEnty);

    public FmsCasherAccount getSlcode(FmsCasherAccount casherAccountEnty);

    public FmsCasherAccount getById(FmsCasherAccount casherAccountEnty);

    public FmsCasherAccount getCashierAccByEmpId(HrEmployees empEnty);

    public boolean findDup(HrEmployees empEnty);
}
