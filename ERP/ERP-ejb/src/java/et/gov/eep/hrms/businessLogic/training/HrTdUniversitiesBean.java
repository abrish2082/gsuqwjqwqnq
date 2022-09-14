/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrTdIspStudents;
import et.gov.eep.hrms.entity.training.HrTdUniversities;
import et.gov.eep.hrms.mapper.training.HrTdUniversitiesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Benin
 */
@Stateless
public class HrTdUniversitiesBean implements HrTdUniversitiesBeanLocal {

    @EJB
    HrTdUniversitiesFacade hrTdUniversitiesFacade;
    @Inject
    HrTdUniversities hrTdUniversities;

    @Override
    public void saveOrUpdate(HrTdUniversities hrTdUniversities) {
        hrTdUniversitiesFacade.saveOrUpdate(hrTdUniversities);
    }

    @Override
    public void edit(HrTdUniversities hrTdUniversities) {
        hrTdUniversitiesFacade.edit(hrTdUniversities);
    }

    @Override
    public List<HrTdUniversities> findByUniversityName(HrTdUniversities hrTdUniversities) {
        return hrTdUniversitiesFacade.findByUniversityName(hrTdUniversities);
    }

    @Override
    public List<HrTdUniversities> findAll() {
        return hrTdUniversitiesFacade.findAll();
    }

    @Override
    public List<HrTdUniversities> findUniversity(String TdUniversitie) {
        return hrTdUniversitiesFacade.findUniversity(TdUniversitie);
    }

    @Override
    public List<HrTdUniversities> findUniversity(HrTdUniversities hrTdUniversities) {
        return hrTdUniversitiesFacade.findUniversityName(hrTdUniversities);
    }

    @Override
    public List<HrTdUniversities> findByUniversityId(HrTdIspStudents hrTdIspStudents) {
        return hrTdUniversitiesFacade.findByUniversityId(hrTdIspStudents);
    }

    @Override
    public boolean checkDuplicationByName(HrTdUniversities hrTdUniversities) {
        return hrTdUniversitiesFacade.checkDuplicationByName(hrTdUniversities);
    }
}
