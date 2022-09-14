/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsLuStoreType;
import et.gov.eep.mms.mapper.MmsLuStoreTypeFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author insa
 */
@Stateless
public class MmsLuStoreTypeBean implements MmsLuStoreTypeBeanLocal {

    @EJB
      MmsLuStoreTypeFacade measureFacade;
    
    @Override
    public List<MmsLuStoreType> findAll() {
       return measureFacade.findAll();
    }

}
