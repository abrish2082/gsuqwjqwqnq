/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.organization;


import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author munir
 */
@Local
public interface HrJobTypesBeanLocal {

    /**
     *
     * @param hrJobTypes
     */
    void create(HrJobTypes hrJobTypes);

    /**
     *
     * @param hrJobTypes
     */
    void edit(HrJobTypes hrJobTypes);

    /**
     *
     * @param hrJobTypes
     */
    void remove(HrJobTypes hrJobTypes);

    /**
     *
     * @param id
     * @return
     */
    HrJobTypes find(Object id);

    /**
     *
     * @return
     */
    List<HrJobTypes> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<HrJobTypes> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();

    /**
     *
     * @param jobType
     * @return
     */
    List<HrJobTypes> returnJobTypes(String jobType);

    /**
     *
     * @param id
     * @return
     */
    HrJobTypes searchJobTypesById(int id);

    /**
     *
     * @param hrJobTypes
     * @return
     */
    public HrJobTypes findAllJobDetail(HrJobTypes hrJobTypes);

    /**
     *
     * @param hrJobTypes
     */
    public void saveOrUpdate(HrJobTypes hrJobTypes);

    /**
     *
     * @param hrJobTypes
     * @return
     */
    public boolean checkDuplicateJob(HrJobTypes hrJobTypes);

    public List<HrJobTypes> readAllJobTypes();

    public List<HrJobTypes> listOfJobType(int departmentId);

    public String totalNoEmpAllowedForJob(int jobId);

    public String noEmpAllowedForJob(int jobId);

    public ArrayList<HrJobTypes> searchByJobCode(HrJobTypes hrJobTypes);

    public HrJobTypes getByJobId(HrJobTypes hrJobTypes);

    public ArrayList<HrJobTypes> searchByJobTitle(HrJobTypes hrJobTypes);

    public HrJobTypes findByJobTitle(HrJobTypes jobTitle);

    public HrJobTypes findBYJobId(Integer jobId);

    public List<ColumnNameResolver> findColumns();

    public ArrayList<HrJobTypes> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, String Col_Value);



}
