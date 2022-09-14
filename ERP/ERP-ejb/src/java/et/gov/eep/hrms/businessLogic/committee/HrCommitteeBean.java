/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.committee;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.committee.HrCommittees;
import et.gov.eep.hrms.entity.lookup.HrLuCommitteeTypes;
import et.gov.eep.hrms.mapper.committee.HrCommitteesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrCommitteeBean implements HrCommitteeBeanLocal {

    @EJB
    HrCommitteesFacade committeesFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public List<HrCommittees> findAll() {
        return committeesFacade.findAll();
    }

    @Override
    public HrCommittees getCommittee(HrCommittees hrCommittees) {
        return committeesFacade.getCommittee(hrCommittees);
    }

    @Override
    public List<HrCommittees> getCommittee() {
        return committeesFacade.getCommitteeList();
    }

    @Override
    public void saveOrUpdate(HrCommittees get) {
        committeesFacade.saveOrUpdate(get);
    }
 @Override
    public void Update(HrCommittees get) {
        committeesFacade.edit(get);
    }
    @Override
    public List<HrCommittees> findByCommitteeName(HrLuCommitteeTypes hrLuCommitteeTypes) {
        return committeesFacade.findByCommitteeName(hrLuCommitteeTypes);
    }

    @Override
    public HrCommittees findByCommitteType(HrLuCommitteeTypes hrLuCommitteeTypes) {
       return committeesFacade.findByCommitteType(hrLuCommitteeTypes);
    }

    @Override
    public List<HrCommittees> findByCommitteeName(HrCommittees hrCommittees) {
        return committeesFacade.findByCommitteeName(hrCommittees);
    }

//    @Override
//    public boolean getCommitteeNameDup(HrCommittees hrCommittees) {
//        return committeesFacade.getCommitteeNameDup(hrCommittees);
//    }

    @Override
    public List<ColumnNameResolver> findColumns() {
       return committeesFacade.findColumns();
    }

    @Override
    public List<HrCommittees> findByParameteredColumnValue(String col_Name_FromTable, String col_Value) {
         return committeesFacade.findByParameteredColumnValue(col_Name_FromTable,col_Value);
    }
    

}
