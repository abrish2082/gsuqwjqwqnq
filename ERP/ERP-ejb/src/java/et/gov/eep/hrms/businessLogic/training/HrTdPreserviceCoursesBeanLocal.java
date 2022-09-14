/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrLuTdPsvcCourseTypes;
import et.gov.eep.hrms.entity.training.HrTdPsvcCourses;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author insa
 */
@Local
public interface HrTdPreserviceCoursesBeanLocal {

    public HrTdPsvcCourses getSelectedRequest(int request);

    public HrTdPsvcCourses saveupdate(HrTdPsvcCourses hrTdPsvcCourses);

    public List<HrLuTdPsvcCourseTypes> categories();

    public List<HrTdPsvcCourses> findByCourse(HrTdPsvcCourses HrTdPsvcCourses);
}
