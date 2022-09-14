/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsLuVatTypeLookup;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Terminal2
 */
@Local
public interface VatTypeLookupBeanLocal {

    public List<PrmsLuVatTypeLookup> findAll();
    
}
