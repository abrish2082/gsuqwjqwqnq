/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.uniform;

import et.gov.eep.hrms.entity.uniform.HrUniform;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Behailu
 */
@Local
public interface uniformBeanLocal {

    public void saveorUpdate(HrUniform hrUniform);

    public List<HrUniform> findAllUniforms();

    public List<HrUniform> findByUniformId(Integer id);

    public HrUniform loadUniformDetail(BigDecimal id);
    
}
