/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsAwardDetail;
import et.gov.eep.prms.mapper.PrmsAwardDetailFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class AwardDetailBean implements AwardDetailBeanLocal {

    @EJB
    private PrmsAwardDetailFacade prmsAwardDetailFacade;
    
    
    @Override
    public List<PrmsAwardDetail> getApprovedWithBidPurchaseTypePRListt(PrmsAward prmsAward) {
        System.out.println("================"
                + "Detail Bean====================");
       return prmsAwardDetailFacade.searchAwardDetailByAwardId(prmsAward);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public List<PrmsAwardDetail> findAll() {
        return prmsAwardDetailFacade.findAll();
    }
    
}
