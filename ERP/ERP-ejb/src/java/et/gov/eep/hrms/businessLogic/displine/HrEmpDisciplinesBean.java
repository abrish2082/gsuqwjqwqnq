/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.displine;

import et.gov.eep.hrms.entity.displine.HrEmpDisciplines;
import et.gov.eep.hrms.mapper.displine.HrEmpDisciplinesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Abdi
 */
@Stateless
public class HrEmpDisciplinesBean implements HrEmpDisciplinesBeanLocal {

    @EJB
    HrEmpDisciplinesFacade hrEmpDisciplinesFacade;

    @Override
    public void saveOrUpdate(HrEmpDisciplines hrEmpDisciplines) {
        hrEmpDisciplinesFacade.saveOrUpdate(hrEmpDisciplines);
    }

    @Override
    public List<HrEmpDisciplines> findPhaseOutList() {
        return hrEmpDisciplinesFacade.findPhaseOutList();
    }

    @Override
    public void edit(HrEmpDisciplines hrEmpDisciplines) {
        hrEmpDisciplinesFacade.edit(hrEmpDisciplines);
    }

}
