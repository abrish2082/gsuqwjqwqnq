/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsStorereq;
import et.gov.eep.mms.entity.MmsStorereqDetail;
import et.gov.eep.mms.mapper.MmsStorereqDetailFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class MmsStorereqDetailBean implements MmsStorereqDetailBeanLocal {
    
    @EJB
    MmsStorereqDetailFacade srDetailFacade;
    
    /**
     *
     * @param inspection
     * @return
     */
    @Override
    public List<MmsStorereqDetail> getlistofSrDetails(MmsStorereq inspection) {
        return srDetailFacade.searchBySrId(inspection);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
