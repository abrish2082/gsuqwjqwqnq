/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.displine;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.displine.HrDisciplineOffenceTypes;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface HrDisciplineOffenceTypesBeanLocal {

    public void save(HrDisciplineOffenceTypes offenceTypes);

    public List<HrDisciplineOffenceTypes> findByOffenceCode();

    public HrDisciplineOffenceTypes displayByOffenceCode(String toString);

    public void edit(HrDisciplineOffenceTypes offenceTypes);

    public void saveOrUpdate(HrDisciplineOffenceTypes disciplineOffenceTypes);

    public List<HrDisciplineOffenceTypes> findByOffenceCode(HrDisciplineOffenceTypes offenceTypes);

    public List<HrDisciplineOffenceTypes> findByOffenceName(HrDisciplineOffenceTypes offenceTypes);

    public List<HrDisciplineOffenceTypes> findAlls();

    public HrDisciplineOffenceTypes findByOffenceNames(HrDisciplineOffenceTypes offenceTypes);

    public boolean checkDuplicationByName(HrDisciplineOffenceTypes offenceTypes);

    public List<HrDisciplineOffenceTypes> findColumnNames();

    public List<HrDisciplineOffenceTypes> searchOffenceType(HrDisciplineOffenceTypes offenceTypes);

    public List<ColumnNameResolver> findColumns();

    public ArrayList<HrDisciplineOffenceTypes> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, String columnValue);

}
