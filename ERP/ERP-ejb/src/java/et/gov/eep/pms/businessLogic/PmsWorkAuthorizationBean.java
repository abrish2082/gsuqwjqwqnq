/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.pms.businessLogic;

import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.pms.entity.PmsWorkAuthorization;
import et.gov.eep.pms.mapper.PmsWorkAuthorizationFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Binyam
 */
@Stateless
public class PmsWorkAuthorizationBean implements PmsWorkAuthorizationBeanLocal {

    @EJB
    PmsWorkAuthorizationFacade pmsWorkAuthorizationfacade;
    
    @Override
    public List<PmsWorkAuthorization> findAll() {
        return pmsWorkAuthorizationfacade.findAll();
    }
    
    @Override
    public List<PmsWorkAuthorization> findNotInLoan() {
        return pmsWorkAuthorizationfacade.findNotInLoan();
    }

    @Override
    public PmsWorkAuthorization findJobID(PmsWorkAuthorization pmsWorkAuthorization) {
        return pmsWorkAuthorizationfacade.findJobId(pmsWorkAuthorization);
    }

    @Override
    public List<PmsWorkAuthorization> findJobNoByGL(FmsGeneralLedger fmsGeneralLedger) {
        return pmsWorkAuthorizationfacade.findJobNoByGL(fmsGeneralLedger);
    }

    @Override
    public PmsWorkAuthorization findByWorkAutId(PmsWorkAuthorization pmsWorkAuthorization) {
        return pmsWorkAuthorizationfacade.findByWorkAutId(pmsWorkAuthorization);
    }
    
    
}
