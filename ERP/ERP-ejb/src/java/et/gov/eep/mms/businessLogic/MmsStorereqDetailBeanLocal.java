/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsStorereq;
import et.gov.eep.mms.entity.MmsStorereqDetail;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface MmsStorereqDetailBeanLocal {

    /**
     *
     * @param inspection
     * @return
     */
    public List<MmsStorereqDetail> getlistofSrDetails(MmsStorereq inspection);
}
