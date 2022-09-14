/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.mapper.PrmsVatTypeLookupFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.prms.entity.PrmsLuVatTypeLookup;

/**
 *
 * @author Terminal2
 */
@Stateless
public class VatTypeLookupBean implements VatTypeLookupBeanLocal {

    @EJB
    PrmsVatTypeLookupFacade prmsVatTypeLookupFacade;

    @Override
    public List<PrmsLuVatTypeLookup> findAll() {
        return prmsVatTypeLookupFacade.findAll();

    }

    // Add business logic below. (Right-click in editor an choose
    // "Insert Code > Add Business Method")
}
