/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.displine;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.displine.HrDisciplineOffencePenality;
import et.gov.eep.hrms.entity.displine.HrDisciplinePenaltyTypes;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface HrDisciplinePenaltyTypesBeanLocal {

    public void save(HrDisciplinePenaltyTypes disciplinePenaltyTypes);

    public List<HrDisciplinePenaltyTypes> findAll();

    public HrDisciplinePenaltyTypes findByPenalityCode(String toString);

    public void Edit(HrDisciplinePenaltyTypes disciplinePenaltyTypes);

    public void saveOrUpdate(HrDisciplinePenaltyTypes disciplinePenaltyTypes);

    public List<HrDisciplinePenaltyTypes> findByPenalityCode(HrDisciplinePenaltyTypes disciplinePenaltyTypes);

    public List<HrDisciplinePenaltyTypes> findByPenalityName(HrDisciplinePenaltyTypes disciplinePenaltyTypes);

    public List<HrDisciplinePenaltyTypes> getPenalityListByName(String penalityName);

    public boolean checkDuplicationByPenaltyName(HrDisciplinePenaltyTypes disciplinePenaltyTypes);

    public List<ColumnNameResolver> findColumns();

    public ArrayList<HrDisciplinePenaltyTypes> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, String columnValue);

}
