/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author insa
 */
@Local
public interface SpcDetailBeanLocal {

    /**
     *
     * @param generatedSpcNo
     * @return
     */
    List<Integer> searchSPCNumber(String generatedSpcNo);
    
}
