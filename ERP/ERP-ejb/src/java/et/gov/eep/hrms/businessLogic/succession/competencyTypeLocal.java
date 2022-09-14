/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.succession;

import et.gov.eep.hrms.entity.succession.HrSmCompetencyTypes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author meles
 */
@Local
public interface competencyTypeLocal {

    public void SaveUpdate(HrSmCompetencyTypes hrSmCompetencyTypes);

    public List<HrSmCompetencyTypes> findbycompetencytype(HrSmCompetencyTypes hrSmCompetencyTypes);

    public void SaveOrUpdate(HrSmCompetencyTypes hrSmCompetencyTypes);

    public List<HrSmCompetencyTypes> findbycompetencyname(HrSmCompetencyTypes hrSmCompetencyTypes);

    public List<HrSmCompetencyTypes> finndall();

    public List<HrSmCompetencyTypes> findAll();

    public List<HrSmCompetencyTypes> findbykmp();

    public List<HrSmCompetencyTypes> abcd();

    public List<HrSmCompetencyTypes> findByKnowledge(String knowledge);
     public boolean searchdup(HrSmCompetencyTypes competencyTypes);

    public List<HrSmCompetencyTypes> findAll(HrSmCompetencyTypes hrSmCompetencyTypes);
}
