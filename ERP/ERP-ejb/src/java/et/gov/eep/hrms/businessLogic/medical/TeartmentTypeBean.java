/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.medical;

import et.gov.eep.hrms.entity.medical.HrLocalMedTreatmentType;
import et.gov.eep.hrms.mapper.medical.HrLocalMedTreatmentTypeFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Ob
 */
@Stateless
public class TeartmentTypeBean implements TeartmentTypeBeanLocal {
    
    @EJB
    HrLocalMedTreatmentTypeFacade hrLocalMedTreatmentTypeFacade;
    
    @Override
    public void save(HrLocalMedTreatmentType hrLocalMedTreatmentType) {
        hrLocalMedTreatmentTypeFacade.create(hrLocalMedTreatmentType);
    }
    
    @Override
    public void edit(HrLocalMedTreatmentType hrLocalMedTreatmentType) {
        hrLocalMedTreatmentTypeFacade.edit(hrLocalMedTreatmentType);
    }
    
    @Override
    public boolean isTreatmentExist(HrLocalMedTreatmentType hrLocalMedTreatmentType) {
        return hrLocalMedTreatmentTypeFacade.isTreatmentExist(hrLocalMedTreatmentType);
        
    }
    
    @Override
    public List<HrLocalMedTreatmentType> findAll() {
        return hrLocalMedTreatmentTypeFacade.findAll();
    }
    
    @Override
    public List<HrLocalMedTreatmentType> findByTreatmentType(String treatment) {
        return hrLocalMedTreatmentTypeFacade.findByTreatmentType(treatment);
    }
    
    @Override
    public HrLocalMedTreatmentType findById(HrLocalMedTreatmentType hrLocalMedTreatmentType) {
        return hrLocalMedTreatmentTypeFacade.findById(hrLocalMedTreatmentType);
    }
    
}
