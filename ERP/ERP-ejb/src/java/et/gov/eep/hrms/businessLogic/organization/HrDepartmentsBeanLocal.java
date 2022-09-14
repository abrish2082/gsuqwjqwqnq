/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.organization;

import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author munir
 */
@Local
public interface HrDepartmentsBeanLocal {

    /**
     *
     * @param hrDepartments
     * @return
     */
    public ArrayList<HrDepartments> getdepartmetInfoByName(HrDepartments hrDepartments);

    /**
     *
     * @param hrDepartment
     * @return
     */
    public HrDepartments getdepartmentInformation(HrDepartments hrDepartment);

    /**
     *
     * @return
     */
    public List<HrDepartments> findAllDepartmentInfo();

    /**
     *
     * @param hrDepartments
     */
    void create(HrDepartments hrDepartments);

    /**
     *
     * @param hrDepartments
     */
    void edit(HrDepartments hrDepartments);

    /**
     *
     * @param hrDepartments
     */
    void remove(HrDepartments hrDepartments);

    /**
     *
     * @param id
     * @return
     */
    HrDepartments find(Object id);

    /**
     *
     * @return
     */
    List<HrDepartments> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<HrDepartments> findRange(int[] range);

    /**
     *
     * @param id
     * @return
     */
    public HrDepartments serchDepById(int id);

    /**
     *
     * @param id
     * @return
     */
    public List<HrDepartments> searchByParentId(int id);

    /**
     *
     * @return
     */
    public List<HrDepartments> searchAllDepSorted();

    /**
     *
     * @return
     */
    int count();

    /**
     *
     * @return
     */
    public List<HrDepartments> getDeptID();
    // for mms use only

    /**
     * munir code
     *
     * @return list of departments
     */
    public List<HrDepartments> findAllDepartment();

    /**
     *
     * @param hrDepartments
     * @return
     */
    public HrDepartments findByDepartmentId(HrDepartments hrDepartments);

    public HrDepartments findByDeptParentId(int parentId);

    public List<HrAddresses> findAllAddressUnit();

    public HrDepartments findByDeptId(int deptId);

    public List<HrDepartments> getDepartementList();

    /**
     * @meles
     */
    
    
    public int deleteDepartmentInfo(HrDepartments hrDepartments);

    public ArrayList<HrDepartments> SearchByDeptId(HrDepartments hrDepartments);

    public List<String> getMmsHrDepColumnNameList();

}
