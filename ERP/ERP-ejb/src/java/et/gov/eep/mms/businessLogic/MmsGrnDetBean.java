/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsGrnDetail;
import et.gov.eep.mms.mapper.MmsGrnDetailFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsGrnDetBean implements MmsGrnDetBeanLocal {

    @EJB
    MmsGrnDetailFacade grnDtlFacade;
    
    @Override
    public List<MmsGrnDetail> findAllGrnInfo() {
     return grnDtlFacade.findAll();
    }

    @Override
    public MmsGrnDetail findInfoById(MmsGrnDetail grnDetail) {
      return   grnDtlFacade.getGrnDetailInfosByDtlId(grnDetail);
    }

    
}
