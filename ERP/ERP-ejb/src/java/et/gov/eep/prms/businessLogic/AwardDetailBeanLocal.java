/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsAwardDetail;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AwardDetailBeanLocal {
    
    public List<PrmsAwardDetail> getApprovedWithBidPurchaseTypePRListt(PrmsAward papmsAward);
         public List<PrmsAwardDetail> findAll();

    
}
