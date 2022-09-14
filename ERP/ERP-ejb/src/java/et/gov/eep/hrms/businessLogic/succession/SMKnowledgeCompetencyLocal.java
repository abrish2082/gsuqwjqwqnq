/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.succession;

import et.gov.eep.hrms.entity.succession.HrSmCompetencyTypes;
import et.gov.eep.hrms.entity.succession.HrSmKnowledgeCompetency;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author meles
 */
@Local
public interface SMKnowledgeCompetencyLocal {

    public void SaveOrUpdate(HrSmKnowledgeCompetency hrSmKnowledgeCompetency);

    public List<Object[]> findbycompet(String skill);

    public List<HrSmCompetencyTypes> findbycompettype(String knowledge);

    public List<Object[]> findbycompett(String knowledge);

    public List<HrSmCompetencyTypes> findbycompetencyname(HrSmKnowledgeCompetency hrSmKnowledgeCompetency);

    public List<HrSmKnowledgeCompetency> findbykmpiD(HrSmKnowledgeCompetency hrSmKnowledgeCompetency);

    public List<HrSmKnowledgeCompetency> findall(HrSmKnowledgeCompetency hrSmKnowledgeCompetency);

    public List<HrSmKnowledgeCompetency> findJobTitle(HrJobTypes hrJobTypes);

    public void create(HrSmKnowledgeCompetency rowSelect);

    public void edit(HrSmKnowledgeCompetency rowSelect);

    public boolean searchdup(HrSmKnowledgeCompetency hrSmKnowledgeCompetency);

    public HrSmKnowledgeCompetency findbyId(HrSmKnowledgeCompetency hrSmKnowledgeCompetency);

    public List<HrSmKnowledgeCompetency> findbyKmpId(HrSmKnowledgeCompetency hrSmKnowledgeCompetency);

    public List<HrSmKnowledgeCompetency> findAllKmpId(HrJobTypes hrJobTypes);

    public boolean searchduplicate(HrSmKnowledgeCompetency hrSmKnowledgeCompetency);
}
