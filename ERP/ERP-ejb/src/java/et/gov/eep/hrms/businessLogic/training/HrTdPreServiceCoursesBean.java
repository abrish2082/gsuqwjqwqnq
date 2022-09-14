/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrLuTdPsvcCourseTypes;
import et.gov.eep.hrms.entity.training.HrTdPsvcCourses;
import et.gov.eep.hrms.mapper.training.HrTdPsvcCoursesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author insa
 */
@Stateless
public class HrTdPreServiceCoursesBean implements HrTdPreserviceCoursesBeanLocal {

    @EJB
    HrTdPsvcCoursesFacade hrTdPsvcCoursesFacade;

    @Override
    public HrTdPsvcCourses getSelectedRequest(int request) {
        return hrTdPsvcCoursesFacade.getSelectedRequest(request);
    }

    @Override
    public HrTdPsvcCourses saveupdate(HrTdPsvcCourses hrTdPsvcCourses) {
        return hrTdPsvcCoursesFacade.saveOrUpdate(hrTdPsvcCourses);
    }

    @Override
    public List<HrLuTdPsvcCourseTypes> categories() {
        return hrTdPsvcCoursesFacade.categoriesList();
    }

    @Override
    public List<HrTdPsvcCourses> findByCourse(HrTdPsvcCourses HrTdPsvcCourses) {
        return hrTdPsvcCoursesFacade.findByCourse(HrTdPsvcCourses);
    }

}
