/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.displine;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.displine.HrDisciplineOffencePenality;
import et.gov.eep.hrms.entity.displine.HrDisciplinePenaltyTypes;
import et.gov.eep.hrms.mapper.displine.HrDisciplinePenaltyTypesFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrDisciplinePenaltyTypesBean implements HrDisciplinePenaltyTypesBeanLocal {

    @EJB
    HrDisciplinePenaltyTypesFacade penaltyTypesFacade;

    @Override
    public void save(HrDisciplinePenaltyTypes disciplinePenaltyTypes) {
        penaltyTypesFacade.create(disciplinePenaltyTypes);
    }

    @Override
    public List<HrDisciplinePenaltyTypes> findAll() {

        return penaltyTypesFacade.findAll();
    }

    @Override
    public HrDisciplinePenaltyTypes findByPenalityCode(String toString) {
        return penaltyTypesFacade.findByPenalityCode(toString);
    }

    @Override
    public void Edit(HrDisciplinePenaltyTypes disciplinePenaltyTypes) {
        penaltyTypesFacade.edit(disciplinePenaltyTypes);
    }

    @Override
    public void saveOrUpdate(HrDisciplinePenaltyTypes disciplinePenaltyTypes) {
        penaltyTypesFacade.saveOrUpdate(disciplinePenaltyTypes);
    }

    @Override
    public List<HrDisciplinePenaltyTypes> findByPenalityCode(HrDisciplinePenaltyTypes disciplinePenaltyTypes) {
        return penaltyTypesFacade.findByPenalityCode(disciplinePenaltyTypes);
    }

    @Override
    public List<HrDisciplinePenaltyTypes> findByPenalityName(HrDisciplinePenaltyTypes disciplinePenaltyTypes) {
        return penaltyTypesFacade.findByPenalityName(disciplinePenaltyTypes);
    }

    @Override
    public List<HrDisciplinePenaltyTypes> getPenalityListByName(String penalityName) {
        return penaltyTypesFacade.getPenalityListByName(penalityName);
    }

    @Override
    public boolean checkDuplicationByPenaltyName(HrDisciplinePenaltyTypes disciplinePenaltyTypes) {
        return penaltyTypesFacade.checkDuplicationByPenaltyName(disciplinePenaltyTypes);
    }

    @Override
    public List<ColumnNameResolver> findColumns() {
        return penaltyTypesFacade.findColumns();
    }

    @Override
    public ArrayList<HrDisciplinePenaltyTypes> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, String columnValue) {
        return penaltyTypesFacade.searchByCol_NameAndCol_Value(columnNameResolver, columnValue);
    }

}
