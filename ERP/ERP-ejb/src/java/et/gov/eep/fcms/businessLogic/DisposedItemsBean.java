/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.fcms.entity.fixedasset.FmsDisposedItems;
import et.gov.eep.fcms.mapper.FmsDisposedItemsFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Me 
 */
@Stateless
public class DisposedItemsBean implements DisposedItemsBeanLocal {

    @EJB
    FmsDisposedItemsFacade fmsDisposedItemsFacade;
    
    @Override
    public void save(FmsDisposedItems fmsDisposedItems) {
        fmsDisposedItemsFacade.create(fmsDisposedItems);
    }
    
}
