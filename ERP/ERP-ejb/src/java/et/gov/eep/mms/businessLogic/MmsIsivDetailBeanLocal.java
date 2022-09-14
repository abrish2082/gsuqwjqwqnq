/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsIsiv;
import et.gov.eep.mms.entity.MmsIsivDetail;
import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface MmsIsivDetailBeanLocal {

    /**
     *
     * @param id
     * @return
     */
    public MmsIsivDetail getDetailbyId(MmsIsiv id);

    public MmsIsivDetail getlastIsivDtlId();
}
