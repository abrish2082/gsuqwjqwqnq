
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
import et.gov.eep.hrms.mapper.training.HrTdIfEducFacade;
import et.gov.eep.hrms.mapper.training.HrTdIfEducSelectedStaffsFacade;
import et.gov.eep.hrms.mapper.training.HrTdIfTrngFacade;
import et.gov.eep.hrms.mapper.training.HrTdIfTrngSelectedStaffsFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Behailu
 */
@Stateless
public class HrInlandAndForeignTrainingBean implements HrInlandAndForeignTrainingBeanLocal {

    @EJB 
    HrTdIfTrngSelectedStaffsFacade HrTdIfTrngSelectedStaffsFacade;
    @EJB
    HrTdIfTrngFacade hrTdIfTrngFacade;
    @EJB
    HrTdIfEducSelectedStaffsFacade hrTdIfEducSelectedStaffsFacade;
    @EJB
    HrTdIfEducFacade hrTdIfEducFacade;
    
     //<editor-fold defaultstate="collapsed" desc=" training methods">
    @Override
    public ArrayList<HrEmployees> SearchByFname(HrEmployees hrEmployees) {
        return HrTdIfTrngSelectedStaffsFacade.searchByname(hrEmployees);
    }
    
    @Override
    public HrEmployees getByFirstName(HrEmployees hrEmployees) {
        return HrTdIfTrngSelectedStaffsFacade.getbyName(hrEmployees);
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public List<HrTdCourses> findallTrainings() {
        return HrTdIfTrngSelectedStaffsFacade.findalltrainings();
    }
    
    @Override
    public List<HrTdTrainerProfiles> findallInstitutions() {
        return HrTdIfTrngSelectedStaffsFacade.findallinstitutions();
    }
    
    @Override
    public HrAddresses findAllAddressUnitAsOne(HrAddresses hrAddresses) {
        return HrTdIfTrngSelectedStaffsFacade.findalladdrsse(hrAddresses);
    }
    
    @Override
    public List<HrAddresses> findAllAddress() {
        return HrTdIfTrngSelectedStaffsFacade.findallAdresses();
    }
    
    @Override
    public void save(HrTdIfTrng hrTdIfTrng) {
        hrTdIfTrngFacade.create(hrTdIfTrng);
    }
    
    @Override
    public List<HrTdIfTrng> findallInlandAndForeignTrainings() {
        return hrTdIfTrngFacade.findAll();
    }
    
    @Override
    public List<HrTdIfTrng> loadTrainings(int filterstatus) {
        return hrTdIfTrngFacade.filterTrainingByLocation(filterstatus);
    }
    
    @Override
    public void update(HrTdIfTrng hrTdIfTrng) {
        hrTdIfTrngFacade.edit(hrTdIfTrng);
    }
     @Override
    public HrTdIfTrng loadInlandAndForeignTrainingDetails(BigDecimal id) {
        return hrTdIfTrngFacade.loadInlandAndForeignTraining(id);
    }
    
    
//</editor-fold>

   

    //<editor-fold defaultstate="collapsed" desc="EDU Methods">
        @Override
    public List<HrLuEducLevels> findallEduLevels() {
        return hrTdIfEducFacade.findallEducationlevels();
    }
    
    @Override
    public List<HrLuEducTypes> findallEduQualififcationList() {
        return hrTdIfEducFacade.findallEducationQualificationList();
    }
    
    @Override
    public void saveEdu(HrTdIfEduc hrTdIfEduc) {
        hrTdIfEducFacade.create(hrTdIfEduc);
    }
    
    @Override
    public void updateEdu(HrTdIfEduc hrTdIfEduc) {
        hrTdIfEducFacade.edit(hrTdIfEduc);
    }
    
    @Override
    public List<HrTdIfEduc> loadEdus(int filterstatus) {
        return hrTdIfEducFacade.filterByLocation(filterstatus);
    }
    
    @Override
    public List<HrTdIfEduc> finallInlandAndFoereignEdu() {
        return hrTdIfEducFacade.findAll();
    }
    @Override
    public HrTdIfEduc loadInlandAndForeignEduDetails(BigDecimal id) {
       return  hrTdIfEducFacade.loadEduDetail(id);
    }
//</editor-fold>

    @Override
    public HrTdIfTrngSelectedStaffs loadInlandAndForeignTraining_staff_Details(BigDecimal id) {
        return HrTdIfTrngSelectedStaffsFacade.findstaffById(id);
    }

    @Override
    public List<HrTdIfTrng> findtrainingByEmpId(Integer id) {
        return hrTdIfTrngFacade.findtrainingByEmpId(id);
    }

    @Override
    public List<HrTdIfEduc> findEducByEmpId(Integer id) {
        return hrTdIfTrngFacade.findEducByEmpId(id);
    }

    


    
}
