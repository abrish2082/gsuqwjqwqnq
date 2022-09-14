/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.uniform;

import et.gov.eep.hrms.entity.uniform.HrUniform;
import et.gov.eep.hrms.mapper.uniform.HrUniformFacade;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Behailu
 */
@Stateless
public class uniformBean implements uniformBeanLocal {
    
    @EJB
    HrUniformFacade hrUniformFacade;

    @Override
    public void saveorUpdate(HrUniform hrUniform) {
        hrUniformFacade.saveOrUpdate(hrUniform);
    }

    @Override
    public List<HrUniform> findAllUniforms() {
        return hrUniformFacade.findAll();
    }

    @Override
    public List<HrUniform> findByUniformId(Integer id) {
        return hrUniformFacade.findByJobId(id);
    }

    @Override
    public HrUniform loadUniformDetail(BigDecimal id) {
       return  hrUniformFacade.loadUniformDetail(id);
    }
}
