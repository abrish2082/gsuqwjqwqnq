/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.mapper.PrmsSpecificationFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author insa
 */
@Stateless
public class SpcDetailBean implements SpcDetailBeanLocal {

    @EJB
    private PrmsSpecificationFacade specificationFacade;

    /**
     *
     * @param generatedSpcNo
     * @return
     */
    @Override
    public List<Integer> searchSPCNumber(String generatedSpcNo) {
        return specificationFacade.getSpcId(generatedSpcNo);
    }
}
