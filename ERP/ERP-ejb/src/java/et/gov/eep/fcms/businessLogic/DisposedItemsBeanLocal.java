/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.fcms.entity.fixedasset.FmsDisposedItems;
import javax.ejb.Local;

/**
 *
 * @author Me
 */
@Local
public interface DisposedItemsBeanLocal {
    public void save(FmsDisposedItems fmsDisposedItems); 
}
