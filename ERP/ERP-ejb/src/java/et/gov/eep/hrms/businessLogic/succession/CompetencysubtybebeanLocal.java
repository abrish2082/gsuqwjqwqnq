/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.succession;

import et.gov.eep.hrms.entity.succession.HrSmCompetencySubtypes;
import et.gov.eep.hrms.entity.succession.HrSmCompetencyTypes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author meles
 */
@Local
public interface CompetencysubtybebeanLocal {

    public void SaveOrUpdate(HrSmCompetencySubtypes hrSmCompetencySubtypes);

    public List<HrSmCompetencySubtypes> findbycompetencyname(HrSmCompetencySubtypes hrSmCompetencySubtypes);

    public List<HrSmCompetencyTypes> findAllCompetency();

    public List<HrSmCompetencySubtypes> findAll();

    public List<HrSmCompetencySubtypes> findAllskill();

    public List<Object[]> findbycompet(String skill);

    public List<HrSmCompetencySubtypes> kmpskill(String skill);

   public boolean searchdup(HrSmCompetencySubtypes hrSmCompetencySubtypes);

    public List<HrSmCompetencySubtypes> findAll(HrSmCompetencySubtypes hrSmCompetencySubtypes);
    
}
