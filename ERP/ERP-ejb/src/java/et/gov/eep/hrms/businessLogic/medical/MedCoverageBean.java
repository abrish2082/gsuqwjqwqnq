/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.medical;

import et.gov.eep.hrms.entity.medical.HrLocalMedCoverage;
import et.gov.eep.hrms.mapper.medical.HrLocalMedCoverageFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author INSA
 */
@Stateless
public class MedCoverageBean implements MedCoverageBeanLocal {

    @EJB
    HrLocalMedCoverageFacade medCoverageFacade;

    @Override
    public void save(HrLocalMedCoverage hrLocalMedCoverage) {
        medCoverageFacade.create(hrLocalMedCoverage);
    }

    @Override
    public void edit(HrLocalMedCoverage hrLocalMedCoverage) {
        medCoverageFacade.edit(hrLocalMedCoverage);
    }
}
