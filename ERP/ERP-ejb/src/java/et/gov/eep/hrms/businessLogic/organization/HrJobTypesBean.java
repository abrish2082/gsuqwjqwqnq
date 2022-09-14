/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.organization;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.mapper.organization.HrJobTypesFacade;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author munir
 */
@Stateless
public class HrJobTypesBean implements HrJobTypesBeanLocal {

    @EJB
    HrJobTypesFacade hrJobTypesFacade;

    @Override
    public void create(HrJobTypes hrJobTypes) {
        hrJobTypesFacade.create(hrJobTypes);
    }

    @Override
    public void edit(HrJobTypes hrJobTypes) {
        hrJobTypesFacade.edit(hrJobTypes);
    }

    @Override
    public void remove(HrJobTypes hrJobTypes) {
        hrJobTypesFacade.remove(hrJobTypes);
    }

    @Override
    public HrJobTypes find(Object id) {
        return hrJobTypesFacade.find(id);
    }

    @Override
    public List<HrJobTypes> findAll() {
        return hrJobTypesFacade.findAll();
    }

    @Override
    public List<HrJobTypes> findRange(int[] range) {
        return hrJobTypesFacade.findRange(range);
    }

    @Override
    public int count() {
        return hrJobTypesFacade.count();
    }

    /**
     * remove and replace by findJobTitle method
     *
     * @param jobType
     * @return
     */
    @Override
    public List<HrJobTypes> returnJobTypes(String jobType) {
        return hrJobTypesFacade.returnJobTypes(jobType);
    }

    @Override
    public HrJobTypes searchJobTypesById(int id) {
        return hrJobTypesFacade.searchJobTypesById(id);
    }

    /**
     *
     * @param hrJobTypes
     * @return
     */
    @Override
    public HrJobTypes findAllJobDetail(HrJobTypes hrJobTypes) {
        return hrJobTypesFacade.findAllJobDetail(hrJobTypes);
    }

    /**
     *
     * @param hrJobTypes
     * @return
     */
    @Override
    public boolean checkDuplicateJob(HrJobTypes hrJobTypes) {
        return hrJobTypesFacade.checkDuplicateJob(hrJobTypes);
    }

    /**
     *
     * @param hrJobTypes
     */
    @Override
    public void saveOrUpdate(HrJobTypes hrJobTypes) {
        hrJobTypesFacade.saveOrUpdate(hrJobTypes);
    }

    @Override
    public List<HrJobTypes> readAllJobTypes() {
        return hrJobTypesFacade.readAllJobTypes();
    }

    @Override
    public List<HrJobTypes> listOfJobType(int departmentId) {
        return hrJobTypesFacade.listOfJobType(departmentId);
    }

    @Override
    public String totalNoEmpAllowedForJob(int jobId) {
        return hrJobTypesFacade.totalNoEmpAllowedForJob(jobId);
    }

    @Override
    public String noEmpAllowedForJob(int jobId) {
        return hrJobTypesFacade.noEmpAllowedForJob(jobId);
    }

    @Override
    public ArrayList<HrJobTypes> searchByJobCode(HrJobTypes hrJobTypes) {
        return hrJobTypesFacade.searchByJobCode(hrJobTypes);
    }

    @Override
    public HrJobTypes getByJobId(HrJobTypes hrJobTypes) {
        return hrJobTypesFacade.getByJobId(hrJobTypes);
    }
    
    @Override
    public ArrayList<HrJobTypes> searchByJobTitle(HrJobTypes hrJobTypes) {
        return hrJobTypesFacade.searchByJobTitle(hrJobTypes);
    }

    @Override
    public HrJobTypes findByJobTitle(HrJobTypes jobTitle) {
        return hrJobTypesFacade.findByJobTitle(jobTitle);
    }

    @Override
    public HrJobTypes findBYJobId(Integer jobId) {
        return hrJobTypesFacade.findBYJobId(jobId);
    }

    @Override
    public List<ColumnNameResolver> findColumns() {
        return hrJobTypesFacade.findColumns();
    }

    @Override
    public ArrayList<HrJobTypes> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, String Col_Value) {
      return hrJobTypesFacade.searchByCol_NameAndCol_Value(columnNameResolver,Col_Value);
    }

    
}
