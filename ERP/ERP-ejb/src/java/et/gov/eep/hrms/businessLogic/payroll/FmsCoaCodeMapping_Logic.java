/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;


import et.gov.eep.fcms.entity.FmsCoaCodeMapping;
import et.gov.eep.hrms.mapper.payroll.FmsCoaCodeMapping_Facade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Hanoc
 */
@Stateless
public class FmsCoaCodeMapping_Logic {

    @EJB
    private FmsCoaCodeMapping_Facade fmsCoaCodeMapping_Facade;

    /**
     *
     * @param codeMapping
     */
    public void saveOrUpdate(FmsCoaCodeMapping codeMapping) {
        fmsCoaCodeMapping_Facade.saveOrUpdate(codeMapping);
    }

    /**
     *
     * @param type
     * @return
     */
    public List<FmsCoaCodeMapping> getFmsCoaCodeMappingBytype(String type) {
        return fmsCoaCodeMapping_Facade.getFmsCoaCodeMappingBytype(type);
    }
}
