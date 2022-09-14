/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrTdIspStudents;
import et.gov.eep.hrms.entity.training.HrTdUniversities;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Benin
 */
@Local
public interface HrTdUniversitiesBeanLocal {

    public void saveOrUpdate(HrTdUniversities hrTdUniversities);

    public void edit(HrTdUniversities hrTdUniversities);

    public List<HrTdUniversities> findByUniversityName(HrTdUniversities hrTdUniversities);

    public List<HrTdUniversities> findAll();
    
     public List<HrTdUniversities> findUniversity(String TdUniversitie);

    public List<HrTdUniversities> findUniversity(HrTdUniversities hrTdUniversities);

    public List<HrTdUniversities> findByUniversityId(HrTdIspStudents hrTdIspStudents);

    public boolean checkDuplicationByName(HrTdUniversities hrTdUniversities);

}
