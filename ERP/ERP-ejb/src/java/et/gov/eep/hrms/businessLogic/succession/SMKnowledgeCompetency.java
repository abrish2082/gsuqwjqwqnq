/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.succession;

import et.gov.eep.hrms.entity.succession.HrSmCompetencyTypes;
import et.gov.eep.hrms.entity.succession.HrSmKnowledgeCompetency;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.mapper.succession.HrSmCompetencyTypesFacade;
import et.gov.eep.hrms.mapper.succession.HrSmKnowledgeCompetencyFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author meles
 */
@Stateless
public class SMKnowledgeCompetency implements SMKnowledgeCompetencyLocal {

    @EJB
    HrSmKnowledgeCompetencyFacade hrSmKnowledgeCompetencyFacade;
    @EJB
    HrSmCompetencyTypesFacade hrSmCompetencyTypesFacade;

    @Override
    public void SaveOrUpdate(HrSmKnowledgeCompetency hrSmKnowledgeCompetency) {
        hrSmKnowledgeCompetencyFacade.saveOrUpdate(hrSmKnowledgeCompetency);
    }

    @Override
    public List<Object[]> findbycompet(String skill) {
        return hrSmCompetencyTypesFacade.findbycompet(skill);
    }
    @Override
    public List<HrSmCompetencyTypes> findbycompettype(String knowledge) {
        return hrSmCompetencyTypesFacade.findbycompett(knowledge);
    }

    @Override
    public List<Object[]> findbycompett(String knowledge) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HrSmCompetencyTypes> findbycompetencyname(HrSmKnowledgeCompetency hrSmKnowledgeCompetency) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HrSmKnowledgeCompetency> findbykmpiD(HrSmKnowledgeCompetency hrSmKnowledgeCompetency) {
        return hrSmKnowledgeCompetencyFacade.findbykmpid(hrSmKnowledgeCompetency);
    }

    @Override
    public List<HrSmKnowledgeCompetency> findall(HrSmKnowledgeCompetency hrSmKnowledgeCompetency) {
        return hrSmKnowledgeCompetencyFacade.findAll(hrSmKnowledgeCompetency);
    }

    @Override
    public List<HrSmKnowledgeCompetency> findJobTitle(HrJobTypes hrJobTypes) {
        return hrSmKnowledgeCompetencyFacade.findJobTitle(hrJobTypes);
    }

    @Override
    public void create(HrSmKnowledgeCompetency rowSelect) {
        hrSmKnowledgeCompetencyFacade.create(rowSelect);
    }

    @Override
    public void edit(HrSmKnowledgeCompetency rowSelect) {
        hrSmKnowledgeCompetencyFacade.edit(rowSelect);
    }

    @Override
    public boolean searchdup(HrSmKnowledgeCompetency hrSmKnowledgeCompetency) {
        return hrSmKnowledgeCompetencyFacade.searchduplicate(hrSmKnowledgeCompetency);
    }

    @Override
    public HrSmKnowledgeCompetency findbyId(HrSmKnowledgeCompetency hrSmKnowledgeCompetency) {
        return hrSmKnowledgeCompetencyFacade.findbyId(hrSmKnowledgeCompetency);
    }

    @Override
    public List<HrSmKnowledgeCompetency> findbyKmpId(HrSmKnowledgeCompetency hrSmKnowledgeCompetency) {
        return hrSmKnowledgeCompetencyFacade.findbyKmpId(hrSmKnowledgeCompetency);
    }

    @Override
    public List<HrSmKnowledgeCompetency> findAllKmpId(HrJobTypes hrJobTypes) {
        return hrSmKnowledgeCompetencyFacade.findAllKmpId(hrJobTypes);
    }

    @Override
    public boolean searchduplicate(HrSmKnowledgeCompetency hrSmKnowledgeCompetency) {
      return hrSmKnowledgeCompetencyFacade.searchduplicatecompetency(hrSmKnowledgeCompetency);
    }
}
