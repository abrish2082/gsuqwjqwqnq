/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrTdTrainerProfiles;
import et.gov.eep.hrms.mapper.training.HrTdTrainerProfilesFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Mac
 */
@Stateless
public class HrTrainerProfilesBean implements HrTrainerProfilesBeanLocal {

    @EJB
    HrTdTrainerProfilesFacade hrTdTrainerProfilesFacade;

    @Override
    public void create(HrTdTrainerProfiles hrTdTrainerProfiles) {
        hrTdTrainerProfilesFacade.create(hrTdTrainerProfiles);
    }

    @Override
    public void update(HrTdTrainerProfiles hrTdTrainerProfiles) {
        hrTdTrainerProfilesFacade.edit(hrTdTrainerProfiles);
    }

    @Override
    public ArrayList<HrTdTrainerProfiles> findByInstitutionName(HrTdTrainerProfiles hrTdTrainerProfiles) {
        return hrTdTrainerProfilesFacade.findByIname(hrTdTrainerProfiles);
    }

    @Override
    public HrTdTrainerProfiles findByInameObj(HrTdTrainerProfiles hrTdTrainerProfiles) {
        return hrTdTrainerProfilesFacade.findByInameObj(hrTdTrainerProfiles);
    }

    @Override
    public HrTdTrainerProfiles findInstituetId(HrTdTrainerProfiles hrTdTrainerProfiles) {
        return hrTdTrainerProfilesFacade.findInstituetId(hrTdTrainerProfiles);
    }

    @Override
    public HrTdTrainerProfiles getInstName(HrTdTrainerProfiles tf) {
        return hrTdTrainerProfilesFacade.getInstName(tf);
    }

    @Override
    public boolean isExist(HrTdTrainerProfiles trainerProfile) {
        return hrTdTrainerProfilesFacade.isExist(trainerProfile);
    }
    
    @Override
    public List<HrTdTrainerProfiles> findByInstName(String institutionName) {
        return hrTdTrainerProfilesFacade.findByInstName(institutionName);
        
    }

    @Override
    public List<HrTdTrainerProfiles> findAll() {
        return hrTdTrainerProfilesFacade.findAll();
    }

}
