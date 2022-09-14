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
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author meles
 */
@Local
public interface HrsmskillcompetencybeanLocal {

   public List<HrSmSkillCompetency> findJobTitle(HrJobTypes hrJobTypes);

    public void SaveOrUpdate(HrSmSkillCompetency hrSmSkillCompetency);

    public List<HrSmCompetencySubtypes> findByKnowledge(String knowledge);

    public List<HrSmCompetencySubtypes> findbycompettype(String knowledge);

    public List<HrSmSkillCompetency> findbykmpiD(HrSmSkillCompetency hrSmSkillCompetency);

    public List<HrSmSkillCompetency> findall(HrSmSkillCompetency hrSmSkillCompetency);

    public List<HrSmSkillCompetency> add();

    public List<HrSmSkillCompetency> findall();

    public List<HrSmSkillCompetency> kmpskill(String skill);
   public void  create(HrSmSkillCompetency rowSelect1);

    public void edit(HrSmSkillCompetency rowSelect1);

    public boolean searchdup(HrSmSkillCompetency hrSmSkillCompetency);

    public List<HrSmSkillCompetency> findbyKmpId(HrSmSkillCompetency hrSmSkillCompetency);

    public boolean searchduplicate(HrSmKnowledgeCompetency hrSmKnowledgeCompetency);

    public boolean searchduplicate(HrSmSkillCompetency hrSmSkillCompetency);

    
}
