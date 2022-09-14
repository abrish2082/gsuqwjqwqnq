/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.fcms.entity.FmsAccountUseTempData;
import et.gov.eep.fcms.mapper.FmsAccountUseTempDataFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/** 
 *
 * @author memube
 */
@Stateless
public class FmsAccountUseTempDataBean implements FmsAccountUseTempDataBeanLocal {
 
    @EJB
    FmsAccountUseTempDataFacade fmsAccountUseTempFacade;

    @Override
    public void edit(FmsAccountUseTempData fmsAccountUseTempData) {
         fmsAccountUseTempFacade.edit(fmsAccountUseTempData);
    }

    @Override 
    public List<FmsAccountUseTempData> getBillingTransaction() {  
        return fmsAccountUseTempFacade.getBillingTransaction();  
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
