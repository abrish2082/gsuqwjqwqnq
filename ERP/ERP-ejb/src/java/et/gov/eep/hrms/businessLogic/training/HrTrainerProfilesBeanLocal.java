/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrTdTrainerProfiles;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Mac
 */
@Local
public interface HrTrainerProfilesBeanLocal {

    public void create(HrTdTrainerProfiles hrTdTrainerProfiles);

    public void update(HrTdTrainerProfiles hrTdTrainerProfiles);

    public ArrayList<HrTdTrainerProfiles> findByInstitutionName(HrTdTrainerProfiles hrTdTrainerProfiles);

    public HrTdTrainerProfiles findByInameObj(HrTdTrainerProfiles hrTdTrainerProfiles);

    public HrTdTrainerProfiles findInstituetId(HrTdTrainerProfiles hrTdTrainerProfiles);

    public HrTdTrainerProfiles getInstName(HrTdTrainerProfiles tf);

    public boolean isExist(HrTdTrainerProfiles trainerProfile);

    public List<HrTdTrainerProfiles> findByInstName(String institutionName);

    public List<HrTdTrainerProfiles> findAll();
}
