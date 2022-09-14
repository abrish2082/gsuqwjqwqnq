/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.succession;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.succession.HrSmCompetency;
import et.gov.eep.hrms.mapper.succession.HrSmCompetencyFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author meles
 */
@Stateless
public class Competency implements CompetencyLocal {

    @EJB
    HrSmCompetencyFacade hrSmCompetencyFacade;

    @Override
    public void SaveOrUpdate(HrSmCompetency hrSmCompetency) {
        hrSmCompetencyFacade.saveOrUpdate(hrSmCompetency);
    }

    @Override
    public List<HrSmCompetency> findbycompetencyname(HrSmCompetency hrSmCompetency) {
        return hrSmCompetencyFacade.findbycompetencyname(hrSmCompetency);
    }

    @Override
    public void FindAll(HrSmCompetency hrSmCompetency) {
        hrSmCompetencyFacade.findAll();
    }

    @Override
    public List<HrSmCompetency> findAllCompetency() {
        return hrSmCompetencyFacade.findAll();
    }

    @Override
    public boolean searchdup(HrSmCompetency hrSmCompetency) {
        return hrSmCompetencyFacade.searchduplicate(hrSmCompetency);
    }

    @Override
    public List<HrSmCompetency> findAllCompetency(HrSmCompetency hrSmCompetency) {
        return hrSmCompetencyFacade.findAll(hrSmCompetency);
    }

    @Override
    public ArrayList<HrSmCompetency> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, String columnValue) {
        return hrSmCompetencyFacade.searchByCol_NameAndCol_Value(columnNameResolver, columnValue);
    }

    @Override
    public List<ColumnNameResolver> findColumns() {
        return hrSmCompetencyFacade.findColumns();
    }
}
