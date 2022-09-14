/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.succession;

import et.gov.eep.hrms.entity.succession.HrSmCompetencyTypes;
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
public class competencyType implements competencyTypeLocal {

@Inject
HrSmCompetencyTypes hrSmCompetencyTypes;

@EJB
HrSmCompetencyTypesFacade hrSmCompetencyTypesFacade;

    @Override
    public void SaveUpdate(HrSmCompetencyTypes hrSmCompetencyTypes) {
        hrSmCompetencyTypesFacade.saveOrUpdate(hrSmCompetencyTypes);
    }

    @Override
    public List<HrSmCompetencyTypes> findbycompetencytype(HrSmCompetencyTypes hrSmCompetencyTypes) {
        return hrSmCompetencyTypesFacade.findbycompetencyname(hrSmCompetencyTypes);
    }

    @Override
    public void SaveOrUpdate(HrSmCompetencyTypes hrSmCompetencyTypes) {
         hrSmCompetencyTypesFacade.saveOrUpdate(hrSmCompetencyTypes);
    }

    @Override
    public List<HrSmCompetencyTypes> findbycompetencyname(HrSmCompetencyTypes hrSmCompetencyTypes) {
        return hrSmCompetencyTypesFacade.findbycompetencyname(hrSmCompetencyTypes);
    }

    @Override
    public List<HrSmCompetencyTypes> finndall() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HrSmCompetencyTypes> findAll() {
       return hrSmCompetencyTypesFacade.findAll();
    }

    @Override
    public List<HrSmCompetencyTypes> findbykmp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HrSmCompetencyTypes> abcd() {
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HrSmCompetencyTypes> findByKnowledge(String knowledge) {
      return hrSmCompetencyTypesFacade.findkmp(knowledge);
    }

    @Override
    public boolean searchdup(HrSmCompetencyTypes competencyTypes) {
     
       return hrSmCompetencyTypesFacade.searchduplicate(competencyTypes);
   
    }

    @Override
    public List<HrSmCompetencyTypes> findAll(HrSmCompetencyTypes hrSmCompetencyTypes) {
        return hrSmCompetencyTypesFacade.findAll(hrSmCompetencyTypes);
    }
}
