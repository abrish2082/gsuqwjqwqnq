/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.organization;

import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.mapper.organization.HrDepartmentsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sadik
 */
@Stateless
public class HrDepartmentsBean implements HrDepartmentsBeanLocal {

    /**
     *
     * @param hrDepartments
     */
    @Override
    public void create(HrDepartments hrDepartments) {
        hrDepartmentsFacade.create(hrDepartments);
    }

    /**
     *
     * @param hrDepartments
     */
    @Override
    public void edit(HrDepartments hrDepartments) {
        hrDepartmentsFacade.edit(hrDepartments);
    }

    /**
     *
     * @param hrDepartments
     */
    @Override
    public void remove(HrDepartments hrDepartments) {
        hrDepartmentsFacade.remove(hrDepartments);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public HrDepartments find(Object id) {
        return hrDepartmentsFacade.find(id);
    }

    /**
     *
     * @return
     */
    @Override
    public List<HrDepartments> findAll() {
        return hrDepartmentsFacade.findAll();
    }

    /**
     *
     * @param range
     * @return
     */
    @Override
    public List<HrDepartments> findRange(int[] range) {
        return hrDepartmentsFacade.findRange(range);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public HrDepartments serchDepById(int id) {
        return hrDepartmentsFacade.serchDepById(id);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public List<HrDepartments> searchByParentId(int id) {
        return hrDepartmentsFacade.searchByParentId(id);
    }

    /**
     *
     * @return
     */
    @Override
    public List<HrDepartments> searchAllDepSorted() {
        return hrDepartmentsFacade.searchAllDepSorted();
    }

    /**
     *
     * @return
     */
    @Override
    public int count() {
        return hrDepartmentsFacade.count();
    }

    /**
     *
     * @return
     */
    @Override
    public List<HrDepartments> getDeptID() {
        return hrDepartmentsFacade.getDeptID();
    }

    /**
     * munir code
     *
     * @return list of departments
     */
    @EJB
    HrDepartmentsFacade hrDepartmentsFacade;

    /**
     *
     * @param hrDepartments
     * @return
     */
    @Override
    public ArrayList<HrDepartments> getdepartmetInfoByName(HrDepartments hrDepartments) {
        return hrDepartmentsFacade.getdepartmetInfoByName(hrDepartments);
    }

    /**
     *
     * @param hrDepartment
     * @return
     */
    @Override
    public HrDepartments getdepartmentInformation(HrDepartments hrDepartment) {
        return hrDepartmentsFacade.getdepartmentInformation(hrDepartment);
    }

    /**
     *
     * @return
     */
    @Override
    public List<HrDepartments> findAllDepartmentInfo() {
        return hrDepartmentsFacade.findAll();
    }

    /**
     *
     * @return
     */
    @Override
    public List<HrDepartments> findAllDepartment() {
        return hrDepartmentsFacade.findAllDepartment();
    }

    /**
     *
     * @param hrDepartments
     * @return
     */
    @Override
    public HrDepartments findByDepartmentId(HrDepartments hrDepartments) {
        return hrDepartmentsFacade.findByDepartmentId(hrDepartments);
    }

    public HrDepartments findByDeptParentId(int parentId) {
        return hrDepartmentsFacade.findByDeptParentId(parentId);
    }

    public List<HrAddresses> findAllAddressUnit() {
        return hrDepartmentsFacade.findAllAddressUnit();
    }

    public HrDepartments findByDeptId(int deptId) {
        return hrDepartmentsFacade.findByDeptID(deptId);
    }

    @Override
    public List<HrDepartments> getDepartementList() {
        return hrDepartmentsFacade.findAll();
    }
    
    /**
     * @meles
     */
    
    @Override
    public int deleteDepartmentInfo(HrDepartments hrDepartments) {
        return hrDepartmentsFacade.deleteDepartmentInfo(hrDepartments);
    }

    @Override
    public ArrayList<HrDepartments> SearchByDeptId(HrDepartments hrDepartments) {
       return hrDepartmentsFacade.SearchByDeptId(hrDepartments);
    }

    @Override
    public List<String> getMmsHrDepColumnNameList() {
        return hrDepartmentsFacade.getMmsHrDepColumnNameList();
    }
    

}
