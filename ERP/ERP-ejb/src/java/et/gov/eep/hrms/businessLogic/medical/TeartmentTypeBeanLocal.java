/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.medical;

import et.gov.eep.hrms.entity.medical.HrLocalMedTreatmentType;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ob
 */
@Local
public interface TeartmentTypeBeanLocal {

    public void save(HrLocalMedTreatmentType hrLocalMedTreatmentType);

    public void edit(HrLocalMedTreatmentType hrLocalMedTreatmentType);

    public boolean isTreatmentExist(HrLocalMedTreatmentType hrLocalMedTreatmentType);

    public List<HrLocalMedTreatmentType> findByTreatmentType(String treatment);

    public List<HrLocalMedTreatmentType> findAll();

    public HrLocalMedTreatmentType findById(HrLocalMedTreatmentType hrLocalMedTreatmentType);
    
}
