/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.succession;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.succession.HrSmCompetency;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author meles
 */
@Local
public interface CompetencyLocal {

    public void SaveOrUpdate(HrSmCompetency hrSmCompetency);

    public List<HrSmCompetency> findbycompetencyname(HrSmCompetency hrSmCompetency);

    public void FindAll(HrSmCompetency hrSmCompetency);

    public List<HrSmCompetency> findAllCompetency();
    
    public boolean searchdup(HrSmCompetency hrSmCompetency);

    public List<HrSmCompetency> findAllCompetency(HrSmCompetency hrSmCompetency);

    public ArrayList<HrSmCompetency> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, String columnValue);

    public List<ColumnNameResolver> findColumns();
}
