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
import et.gov.eep.fcms.entity.pettyCash.FmsPettyCashReplenishment;
import et.gov.eep.fcms.mapper.pettyCash.FmsPettyCashReplenishmentFacade;

/**
 *
 * @author memube
 */
@Stateless
public class FmsPettyCashReplenishmentBean implements FmsPettyCashReplenishmentBeanLocal {

    @EJB
    FmsPettyCashReplenishmentFacade fmsPettyCashReplenishmentFacade;

    @Override
    public void create(FmsPettyCashReplenishment fmsPettyCashReplenishment) {
        fmsPettyCashReplenishmentFacade.create(fmsPettyCashReplenishment);
    }

    @Override
    public void edit(FmsPettyCashReplenishment fmsPettyCashReplenishment) {
        fmsPettyCashReplenishmentFacade.edit(fmsPettyCashReplenishment);
    }

    @Override
    public List<FmsPettyCashReplenishment> findPCRByCashierIdAndWfCheckRejectValue(FmsCasherAccount fmsCasherAccount, int CHECK_REJECT_VALUE) {
        return fmsPettyCashReplenishmentFacade.findPCRByCashierIdAndWfCheckRejectValue(fmsCasherAccount, CHECK_REJECT_VALUE);
    }

    @Override
    public List<FmsPettyCashReplenishment> findPCRByWfStatus(int wfStatus) {
        return fmsPettyCashReplenishmentFacade.findPCRByWfStatus(wfStatus);
    }

    @Override
    public List<FmsPettyCashReplenishment> findPCRByCashierId(FmsCasherAccount fmsCasherAccount) {
        return fmsPettyCashReplenishmentFacade.findPCRByCashierId(fmsCasherAccount);
    }

    @Override
    public FmsPettyCashReplenishment getNonReplenishedByCashierId(FmsCasherAccount fmsCasherAccount) {
        return fmsPettyCashReplenishmentFacade.getNonReplenishedByCashierId(fmsCasherAccount);
    }

    @Override
    public List<FmsPettyCashReplenishment> findPCRByCashierName(FmsCasherAccount fmsCasherAccount) {
        return fmsPettyCashReplenishmentFacade.findPCRByCashierName(fmsCasherAccount);
    }

    @Override
    public List<FmsPettyCashReplenishment> findAll() {
        return fmsPettyCashReplenishmentFacade.findAll();
    }

    @Override
    public List<FmsPettyCashReplenishment> findAllPCR() {
        return fmsPettyCashReplenishmentFacade.findAllPCR();
    }
}
