/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.fcms.entity.FmsAccountUseTempData;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author memube
 */
@Local
public interface FmsAccountUseTempDataBeanLocal {

    public void edit(FmsAccountUseTempData fmsAccountUseTempData); 

    public List<FmsAccountUseTempData> getBillingTransaction();
}
