/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsRmg;
import et.gov.eep.mms.entity.MmsRmgDetail;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface MmsRmgdetailBeanLocal {

    /**
     *
     * @param id
     * @return
     */
    public MmsRmgDetail getDetailbyId(MmsRmg id);
}
