/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import et.gov.eep.hrms.entity.training.HrTdPsvcTraineeDetails;
import et.gov.eep.hrms.entity.training.HrTdPsvcTrainees;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author insa
 */
@Local
public interface HrTdPreserviceTraineesBeanLocal {

    public void saveOrUpdate(HrTdPsvcTrainees hrTdPsvcTrainees);

    public List<HrLuEducLevels> findEducationLevel();

    public List<HrLuEducTypes> findByEducationQuality();

    public List<HrTdPsvcTrainees> findall();

    public ArrayList<HrTdPsvcTrainees> findByyear(HrTdPsvcTrainees hrTdPsvcTrainees);

    public HrTdPsvcTraineeDetails searchById(HrTdPsvcTraineeDetails hrTdPsvcDetails);
}
