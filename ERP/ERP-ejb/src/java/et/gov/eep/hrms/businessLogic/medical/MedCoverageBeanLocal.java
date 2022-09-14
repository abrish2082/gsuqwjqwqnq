/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.medical;

import et.gov.eep.hrms.entity.medical.HrLocalMedCoverage;
import javax.ejb.Local;

/**
 *
 * @author INSA
 */
@Local
public interface MedCoverageBeanLocal {

    public void save(HrLocalMedCoverage hrLocalMedCoverage);

    public void edit(HrLocalMedCoverage hrLocalMedCoverage);
    
}
