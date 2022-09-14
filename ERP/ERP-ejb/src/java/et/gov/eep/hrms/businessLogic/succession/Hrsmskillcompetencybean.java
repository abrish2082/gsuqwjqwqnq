/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.succession;

import et.gov.eep.hrms.entity.succession.HrSmCompetencySubtypes;
import et.gov.eep.hrms.entity.succession.HrSmSkillCompetency;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.succession.HrSmKnowledgeCompetency;
import et.gov.eep.hrms.mapper.succession.HrSmSkillCompetencyFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author meles
 */
@Stateless
public class Hrsmskillcompetencybean implements HrsmskillcompetencybeanLocal {

   @EJB
   HrSmSkillCompetencyFacade hrSmSkillCompetencyFacade ;

    @Override
    public void SaveOrUpdate(HrSmSkillCompetency hrSmSkillCompetency) {
        hrSmSkillCompetencyFacade.saveOrUpdate(hrSmSkillCompetency);
    }

  
    @Override
    public List<HrSmCompetencySubtypes> findbycompettype(String skill) {
       return hrSmSkillCompetencyFacade.findkmpskill(skill);
    }

    @Override
    public List<HrSmCompetencySubtypes> findByKnowledge(String knowledge) {
       return hrSmSkillCompetencyFacade.findkmpskill(knowledge);
    }

    @Override
    public List<HrSmSkillCompetency> findbykmpiD(HrSmSkillCompetency hrSmSkillCompetency) {
        return hrSmSkillCompetencyFacade.findbykmpiD(hrSmSkillCompetency);
    }

    @Override
    public List<HrSmSkillCompetency> findall(HrSmSkillCompetency hrSmSkillCompetency) {
        return hrSmSkillCompetencyFacade.findAll(hrSmSkillCompetency);
    }
    
       @Override
  public void create(HrSmSkillCompetency rowselect1) {
    hrSmSkillCompetencyFacade.create(rowselect1);
    }

    @Override
    public List<HrSmSkillCompetency> add() {
        return hrSmSkillCompetencyFacade.add();
    }

    @Override
    public List<HrSmSkillCompetency> findall() {
      return hrSmSkillCompetencyFacade.add();
    }

    @Override
    public List<HrSmSkillCompetency> kmpskill(String skill) {
      return hrSmSkillCompetencyFacade.kmpskill(skill);  
    }

    @Override
    public List<HrSmSkillCompetency> findJobTitle(HrJobTypes hrJobTypes) {
        return hrSmSkillCompetencyFacade.findJobTitle(hrJobTypes);
    }

    @Override
    public void edit(HrSmSkillCompetency rowselect1) {
        hrSmSkillCompetencyFacade.edit(rowselect1);
    }

    @Override
    public boolean searchdup(HrSmSkillCompetency hrSmSkillCompetency) {
       return hrSmSkillCompetencyFacade.searchduplicate(hrSmSkillCompetency);
    }

    @Override
    public List<HrSmSkillCompetency> findbyKmpId(HrSmSkillCompetency hrSmSkillCompetency) {
        return hrSmSkillCompetencyFacade.findbyKmpId(hrSmSkillCompetency);
    }

    @Override
    public boolean searchduplicate(HrSmKnowledgeCompetency hrSmKnowledgeCompetency) {
        return hrSmSkillCompetencyFacade.searchduplicate(hrSmKnowledgeCompetency);  
    }

    @Override
    public boolean searchduplicate(HrSmSkillCompetency hrSmSkillCompetency) {
       return hrSmSkillCompetencyFacade.searchduplicatexist(hrSmSkillCompetency);  
    }

   

  
}
