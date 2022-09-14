/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import et.gov.eep.hrms.entity.training.HrTdCourses;
import et.gov.eep.hrms.entity.training.HrTdIfEduc;
import et.gov.eep.hrms.entity.training.HrTdIfTrng;
import et.gov.eep.hrms.entity.training.HrTdIfTrngSelectedStaffs;
import et.gov.eep.hrms.entity.training.HrTdTrainerProfiles;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Behailu
 */
@Local
public interface HrInlandAndForeignTrainingBeanLocal {

    public ArrayList<HrEmployees> SearchByFname(HrEmployees hrEmployees);

    public HrEmployees getByFirstName(HrEmployees hrEmployees);

    public List<HrTdCourses> findallTrainings();

    public List<HrTdTrainerProfiles> findallInstitutions();

    public HrAddresses findAllAddressUnitAsOne(HrAddresses hrAddresses);

    public List<HrAddresses> findAllAddress();

    public void save(HrTdIfTrng hrTdIfTrng);

    public List<HrTdIfTrng> findallInlandAndForeignTrainings();

    public List<HrTdIfTrng> loadTrainings(int filterstatus);

    public void update(HrTdIfTrng hrTdIfTrng);

    public HrTdIfTrng loadInlandAndForeignTrainingDetails(BigDecimal id);

    public List<HrLuEducLevels> findallEduLevels();

    public List<HrLuEducTypes> findallEduQualififcationList();

    public void saveEdu(HrTdIfEduc hrTdIfEduc);

    public void updateEdu(HrTdIfEduc hrTdIfEduc);

    public List<HrTdIfEduc> loadEdus(int filterstatus);

    public List<HrTdIfEduc> finallInlandAndFoereignEdu();

    public HrTdIfEduc loadInlandAndForeignEduDetails(BigDecimal id);

    public HrTdIfTrngSelectedStaffs loadInlandAndForeignTraining_staff_Details(BigDecimal id);

    public List<HrTdIfTrng> findtrainingByEmpId(Integer id);

    public List<HrTdIfEduc> findEducByEmpId(Integer id);

  

    
    
}
