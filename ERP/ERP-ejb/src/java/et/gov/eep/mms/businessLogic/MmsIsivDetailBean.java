/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsIsiv;
import et.gov.eep.mms.entity.MmsIsivDetail;
import et.gov.eep.mms.mapper.MmsIsivDetailFacade;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.ejb.EJB;

/**
 *
 * @author user
 */
@Stateless
public class MmsIsivDetailBean implements MmsIsivDetailBeanLocal {

    @EJB
    MmsIsivDetailFacade isivdetailfacade;

    /**
     *
     * @param id
     * @return
     */
    @Override
    public MmsIsivDetail getDetailbyId(MmsIsiv id) {
        return isivdetailfacade.getDetailbyId(id);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public MmsIsivDetail getlastIsivDtlId() {
       return isivdetailfacade.getlastIsivDtlId();
    }
}
