/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.displine;

import et.gov.eep.hrms.entity.displine.HrDisciplineOffenceTypes;
import et.gov.eep.hrms.entity.displine.HrDisciplinePenlitys;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface HrDisciplinePenlitysBeansLocal {

    public List<HrDisciplinePenlitys> findByOffenceId(HrDisciplineOffenceTypes offenceTypes);
    
}
