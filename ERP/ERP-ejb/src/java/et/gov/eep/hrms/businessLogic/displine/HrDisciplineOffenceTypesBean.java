/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.displine;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.displine.HrDisciplineOffenceTypes;
import et.gov.eep.hrms.mapper.displine.HrDisciplineOffenceTypesFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrDisciplineOffenceTypesBean implements HrDisciplineOffenceTypesBeanLocal {

    @EJB
    HrDisciplineOffenceTypesFacade offenceTypesFacade;

    @Override
    public void save(HrDisciplineOffenceTypes offenceTypes) {
        offenceTypesFacade.create(offenceTypes);
    }

    @Override
    public List<HrDisciplineOffenceTypes> findByOffenceCode() {
        return offenceTypesFacade.findAll();
    }

    @Override
    public HrDisciplineOffenceTypes displayByOffenceCode(String toString) {
        return offenceTypesFacade.displayByOffenceCode(toString);
    }

    @Override
    public void edit(HrDisciplineOffenceTypes offenceTypes) {
        offenceTypesFacade.edit(offenceTypes);
    }

    @Override
    public void saveOrUpdate(HrDisciplineOffenceTypes disciplineOffenceTypes) {
        offenceTypesFacade.saveOrUpdate(disciplineOffenceTypes);
    }

    @Override
    public List<HrDisciplineOffenceTypes> findByOffenceCode(HrDisciplineOffenceTypes offenceTypes) {
        return offenceTypesFacade.findByOffenceCode(offenceTypes);
    }

    @Override
    public List findByOffenceName(HrDisciplineOffenceTypes offenceTypes) {
        return offenceTypesFacade.findByOffenceName(offenceTypes);
    }

    @Override
    public List<HrDisciplineOffenceTypes> findAlls() {
        return offenceTypesFacade.findAll();
    }

    @Override
    public HrDisciplineOffenceTypes findByOffenceNames(HrDisciplineOffenceTypes offenceTypes) {
        return offenceTypesFacade.findByOffenceNames(offenceTypes);
    }

    @Override
    public boolean checkDuplicationByName(HrDisciplineOffenceTypes offenceTypes) {
        return offenceTypesFacade.checkDuplicationByName(offenceTypes);
    }

    @Override
    public List<HrDisciplineOffenceTypes> findColumnNames() {
        return offenceTypesFacade.findColumnNames();
    }

    @Override
    public List<HrDisciplineOffenceTypes> searchOffenceType(HrDisciplineOffenceTypes offenceTypes) {
        return offenceTypesFacade.searchOffenceType(offenceTypes);
    }

    @Override
    public List<ColumnNameResolver> findColumns() {
        return offenceTypesFacade.findColumns();
    }

    @Override
    public ArrayList<HrDisciplineOffenceTypes> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, String columnValue) {
        return offenceTypesFacade.searchByCol_NameAndCol_Value(columnNameResolver, columnValue);
    }

}
