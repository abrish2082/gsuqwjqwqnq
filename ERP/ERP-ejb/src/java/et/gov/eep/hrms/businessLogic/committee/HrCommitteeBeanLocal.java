/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.committee;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.committee.HrCommittees;
import et.gov.eep.hrms.entity.lookup.HrLuCommitteeTypes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface HrCommitteeBeanLocal {

    public List<HrCommittees> findAll();

    public HrCommittees getCommittee(HrCommittees hrCommittees);

    public List<HrCommittees> getCommittee();

//    public void saveOrUpdate(HrCommittees get);

    public List<HrCommittees> findByCommitteeName(HrLuCommitteeTypes hrLuCommitteeTypes);

    public HrCommittees findByCommitteType(HrLuCommitteeTypes hrLuCommitteeTypes);

    public List<HrCommittees> findByCommitteeName(HrCommittees hrCommittees);

//    public boolean getCommitteeNameDup(HrCommittees hrCommittees);

    public void saveOrUpdate(HrCommittees get);

    public void Update(HrCommittees get);

    public List<ColumnNameResolver> findColumns();

    public List<HrCommittees> findByParameteredColumnValue(String col_Name_FromTable, String col_Value);

}
