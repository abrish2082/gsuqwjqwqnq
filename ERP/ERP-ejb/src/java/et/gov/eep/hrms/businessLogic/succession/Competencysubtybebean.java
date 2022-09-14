/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.succession;

import et.gov.eep.hrms.entity.succession.HrSmCompetencySubtypes;
import et.gov.eep.hrms.entity.succession.HrSmCompetencyTypes;
import et.gov.eep.hrms.mapper.succession.HrSmCompetencySubtypesFacade;
import et.gov.eep.hrms.mapper.succession.HrSmCompetencyTypesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author meles
 */
@Stateless
public class Competencysubtybebean implements CompetencysubtybebeanLocal {
 @EJB
   HrSmCompetencySubtypesFacade hrSmCompetencySubtypesFacade;
@EJB
HrSmCompetencyTypesFacade hrSmCompetencyTypesFacade;

    @Override
    public void SaveOrUpdate(HrSmCompetencySubtypes hrSmCompetencySubtypes) {
        hrSmCompetencySubtypesFacade.saveOrUpdate(hrSmCompetencySubtypes);
    }

    @Override
    public List<HrSmCompetencySubtypes> findbycompetencyname(HrSmCompetencySubtypes hrSmCompetencySubtypes) {
       return hrSmCompetencySubtypesFacade.findbycompetencysubtypes(hrSmCompetencySubtypes);
    }

    @Override
    public List<HrSmCompetencyTypes> findAllCompetency() {
        return hrSmCompetencyTypesFacade.findAll();
    }

    @Override
    public List<HrSmCompetencySubtypes> findAll() {
       return hrSmCompetencySubtypesFacade.findAll();
    }

    @Override
    public List<HrSmCompetencySubtypes> findAllskill() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 @Override
    public List<Object[]> findbycompet(String skill) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HrSmCompetencySubtypes> kmpskill(String skill) {
        return hrSmCompetencySubtypesFacade.kmpskill(skill);
    }

    @Override
    public boolean searchdup(HrSmCompetencySubtypes hrSmCompetencySubtypes) {
       return hrSmCompetencySubtypesFacade.searchduplicate(hrSmCompetencySubtypes);
    }

    @Override
    public List<HrSmCompetencySubtypes> findAll(HrSmCompetencySubtypes hrSmCompetencySubtypes) {
       return hrSmCompetencySubtypesFacade.findAll(hrSmCompetencySubtypes);
    }

}
