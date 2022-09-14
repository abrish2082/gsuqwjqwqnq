/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsItemType;
import et.gov.eep.mms.mapper.MmsItemTypeFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class ItemTypeBean implements ItemTypeBeanLocal {

    @EJB
    MmsItemTypeFacade typeFacade;
   
    /**
     *
     * @return
     */
    @Override
    public List<MmsItemType> findAll() {
       return typeFacade.findAll();
    }
}
