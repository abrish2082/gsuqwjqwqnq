/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import javax.ejb.Local;

/**
 *
 * @author mora1
 */
@Local
public interface EthiopianCalendarBeanLocal {

    public String getEthiopianCurrentDate();
    
}
