/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.pettyCash;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.pettyCash.FmsCasherAccount;
import et.gov.eep.fcms.entity.pettyCash.FmsPettyCashReplenishment;

/**
 *
 * @author memube
 */
@Local
public interface FmsPettyCashReplenishmentBeanLocal {

    public void create(FmsPettyCashReplenishment fmsPettyCashReplenishment);

    public void edit(FmsPettyCashReplenishment fmsPettyCashReplenishment);

    public FmsPettyCashReplenishment getNonReplenishedByCashierId(FmsCasherAccount fmsCasherAccount);

    public List<FmsPettyCashReplenishment> findPCRByCashierIdAndWfCheckRejectValue(FmsCasherAccount fmsCasherAccount, int CHECK_REJECT_VALUE);

    public List<FmsPettyCashReplenishment> findPCRByWfStatus(int wfStatus);

    public List<FmsPettyCashReplenishment> findPCRByCashierId(FmsCasherAccount fmsCasherAccount);

    public List<FmsPettyCashReplenishment> findPCRByCashierName(FmsCasherAccount fmsCasherAccount);

    public List<FmsPettyCashReplenishment> findAll();

    public List<FmsPettyCashReplenishment> findAllPCR();
}
