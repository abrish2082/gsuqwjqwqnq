/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsRmg;
import et.gov.eep.mms.entity.MmsRmgDetail;
import et.gov.eep.mms.mapper.MmsRmgDetailFacade;
import javax.ejb.Stateless;
import javax.ejb.EJB;

/**
 *
 * @author user
 */
@Stateless
public class MmsRmgdetailBean implements MmsRmgdetailBeanLocal {

   @EJB
   MmsRmgDetailFacade rmgdetailFacade;

    /**
     *
     * @param id
     * @return
     */
    @Override
    public MmsRmgDetail getDetailbyId(MmsRmg id) {
       return rmgdetailFacade.getDetailbyId(id);
    }
}
